package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
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
        try {
            val cryptocurrency = cryptoService.getCrypto(orderDTO.cryptocurrency)
            validatePriceMargin(orderDTO.price, cryptocurrency)
            val user = userService.getUser(orderDTO.userId)
            val intentionType = orderDTO.type
            val arsPrice = calculateArsPrice(orderDTO.amount, orderDTO.price, intentionType)
            return OrderMapper.toModel(orderDTO, user, cryptocurrency, intentionType, arsPrice).also { orderRepository.save(it) }
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun getActiveOrders(): List<Order> {
        try {
            val activeOrders = orderRepository.findByIsActiveTrue()
            if (activeOrders.isEmpty()) throw Exception("There are no active orders")
            return updateUsdToArsRate(activeOrders)
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun getOrder(id: Long): Order {
        val order = orderRepository.findById(id).orElseThrow { Exception("Order with id $id not found") }
        return updateUsdToArsRate(order)
    }

    override fun update(order: Order): Order {
        return orderRepository.save(order)
    }

    private fun validatePriceMargin(price: Double, cryptocurrency: Cryptocurrency) {
        if (cryptocurrency.isAboveMargin(5.0,price))
            throw Exception("Price out of margin range of 5% above the last price of the cryptocurrency")
        if (cryptocurrency.isBelowMargin(5.0,price))
            throw Exception("Price out of margin range of 5% below the last price of the cryptocurrency")
    }

    private fun calculateArsPrice(amount: Double, price: Double, intentionType: IntentionType): Double {
        val usdToArsRate = getUsdToArsRate(intentionType)
        return (amount * price) * usdToArsRate
    }

    private fun getUsdToArsRate(intentionType: IntentionType): Double {
        return if (intentionType == IntentionType.BUY) {
            cotizationService.getRateUsdToArs().compra!!
        } else {
            cotizationService.getRateUsdToArs().venta!!
        }
    }

    private fun updateUsdToArsRate(activeOrders: List<Order>): List<Order> {
        activeOrders.forEach {
            updateUsdToArsRate(it)
        }
        return activeOrders
    }

    private fun updateUsdToArsRate(order: Order): Order {
        order.priceARS = calculateArsPrice(order.amount!!, order.price!!, order.type!!)
        return update(order)
    }

}