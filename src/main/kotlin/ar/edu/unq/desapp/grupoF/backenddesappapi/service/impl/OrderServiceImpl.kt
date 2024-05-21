package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
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
        var user = userService.getUser(orderDTO.userId)
        var cryptocurrency = cryptoService.getCrypto(orderDTO.cryptocurrency)
        var intentionType = orderDTO.type
        var usdArsCotization: Double
        if (intentionType == IntentionType.BUY) {
            usdArsCotization = cotizationService.getRateUsdToArs().compra!!
        }else
        {
            usdArsCotization = cotizationService.getRateUsdToArs().venta!!
        }
        var order = OrderMapper.toModel(orderDTO, user, cryptocurrency, intentionType, usdArsCotization)
        orderRepository.save(order)
        return order
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
}