package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO

interface IOrderService {
    fun createOrder(orderDTO: OrderRequestDTO) : Order
    fun getActiveOrders(): List<Order>
    fun getOrder(id: Long): Order
    fun update(order: Order): Order
    fun updateArsRate(): List<Order>
}