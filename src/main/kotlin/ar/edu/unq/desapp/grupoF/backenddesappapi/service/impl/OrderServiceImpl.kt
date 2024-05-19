package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : IOrderService {
    override fun createOrder(order: Order) : Order {
        orderRepository.save(order)
        return order
    }

    override fun getActiveOrders(): List<Order> {
        return orderRepository.findByIsActiveTrue()
    }

    override fun getOrder(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { Exception("Order with id $id not found") }
    }
}