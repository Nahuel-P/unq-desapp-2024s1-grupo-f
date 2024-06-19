package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.DolarApiClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl : IOrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository
    @Autowired
    private lateinit var userService: IUserService
    @Autowired
    private lateinit var cryptoService: ICryptoService
    private var cotizationService: DolarApiClient = DolarApiClient()

    override fun createOrder(orderDTO: OrderRequestDTO) : Order {
        val user = userService.getUser(orderDTO.userId)
        val cryptocurrency = cryptoService.getCrypto(orderDTO.cryptocurrency)
        val intentionType = orderDTO.type
        val usdToArsRate = getUsdToArsRate(intentionType)
        val arsPrice = calculateArsPrice(orderDTO.amount, orderDTO.price, usdToArsRate)
        return OrderMapper.toModel(orderDTO, user, cryptocurrency, intentionType, arsPrice).also {
            orderRepository.save(it)
        }
    }

    override fun getActiveOrders(): List<Order> {
        return orderRepository.findByIsActiveTrue()
    }

    override fun getOrder(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { Exception("Order with id $id not found") }
    }

    override fun update(order: Order): Order {
        return orderRepository.save(order)
    }

    private fun getUsdToArsRate(intentionType: IntentionType): Double {
        return if (intentionType == IntentionType.BUY) {
            cotizationService.getRateUsdToArs().compra!!
        } else {
            cotizationService.getRateUsdToArs().venta!!
        }
    }

    private fun calculateArsPrice(amount: Double, price: Double, usdArsCotization: Double): Double {
        return (amount * price) * usdArsCotization
    }
}