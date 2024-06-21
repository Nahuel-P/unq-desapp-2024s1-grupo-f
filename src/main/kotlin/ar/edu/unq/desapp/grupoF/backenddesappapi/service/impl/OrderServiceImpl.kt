package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.DolarApiClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val commonService: ICommonService,
) : IOrderService {

    private val rateService: DolarApiClient = DolarApiClient()
    override fun createOrder(orderDTO: OrderRequestDTO): Order {
        try {
            val cryptocurrency = commonService.getCrypto(orderDTO.cryptocurrency)
            validatePriceMargin(orderDTO.price, cryptocurrency)
            val user = commonService.getUser(orderDTO.userId)
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
            // mo me gusa este update, con el task qwe corre cada x minutos se va
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
        if (cryptocurrency.isAboveMarginPrice(5.0, price)) {
            throw Exception("Price out of margin range of 5% above the last price of the cryptocurrency")
        }
        if (cryptocurrency.isBelowMarginPrice(5.0, price)) {
            throw Exception("Price out of margin range of 5% below the last price of the cryptocurrency")
        }
    }

    private fun calculateArsPrice(amount: Double, price: Double, intentionType: IntentionType): Double {
        val usdToArsRate = getUsdToArsRate(intentionType)
        return (amount * price) * usdToArsRate
    }

    private fun getUsdToArsRate(intentionType: IntentionType): Double {
        return if (intentionType == IntentionType.BUY) {
            rateService.getRateUsdToArs().compra!!
        } else {
            rateService.getRateUsdToArs().venta!!
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
