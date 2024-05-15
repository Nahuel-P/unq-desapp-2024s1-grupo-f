package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User

interface IOrderService {
    fun createOrder(user: User,
                    cryptocurrency: String,
                    amount: Double,
                    price: Double,
                    type: String) : Order
    fun getActiveOrders(): List<Order>
}