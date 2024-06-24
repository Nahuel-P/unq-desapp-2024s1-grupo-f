package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.DolarApiClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
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

            val user = commonService.getUser(orderDTO.userId)
            val intentionType = orderDTO.type
            val arsPrice = calculateArsPrice(orderDTO.amount, orderDTO.price, intentionType)
            val newOrder = OrderMapper.toModel(orderDTO, user, cryptocurrency, intentionType, arsPrice)
            validatePriceMargin(newOrder)
            return orderRepository.save(newOrder)
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun getActiveOrders(): List<Order> {
        try {
            val activeOrders = orderRepository.findByIsActiveTrue()
            if (activeOrders.isEmpty()) throw Exception("There are no active orders")
            return activeOrders
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun getOrder(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { Exception("Order with id $id not found") }
    }

    override fun update(order: Order): Order {
        return orderRepository.save(order)
    }

    @Scheduled(fixedRate = 600000)
    override fun updateArsRate(): List<Order> {
        val activeOrders = orderRepository.findByIsActiveTrue()
        val arsRateBuy = getUsdToArsRate(IntentionType.BUY)
        val arsRateSell = getUsdToArsRate(IntentionType.SELL)
        return updateUsdToArsRate(activeOrders, arsRateBuy, arsRateSell)
    }

    private fun validatePriceMargin(order: Order) {
        if (order.isAboveMarginPrice(5.0)) {
            throw Exception("Price out of margin range of 5% above the last price of the cryptocurrency")
        }
        if (order.isBelowMarginPrice(5.0)) {
            throw Exception("Price out of margin range of 5% below the last price of the cryptocurrency")
        }
    }

    private fun updateUsdToArsRate(activeOrders: List<Order>, arsRateBuy: Double, arsRateSell: Double): List<Order> {
        activeOrders.forEach {
            it.calculateArsPrice(arsRateBuy, arsRateSell)
            update(it)
        }
        return activeOrders
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
}
