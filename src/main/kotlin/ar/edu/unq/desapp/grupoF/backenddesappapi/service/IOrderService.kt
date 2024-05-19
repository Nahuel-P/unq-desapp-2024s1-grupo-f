package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order

interface IOrderService {
    fun createOrder(order: Order) : Order
    fun getActiveOrders(): List<Order>
    fun getOrder(id: Long): Order
}