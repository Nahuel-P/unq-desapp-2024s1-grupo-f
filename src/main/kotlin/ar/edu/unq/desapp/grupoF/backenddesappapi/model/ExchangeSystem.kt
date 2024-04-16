package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder

class ExchangeSystem {

    var users: MutableSet<User> = mutableSetOf()
    var orders: MutableList<Order> = mutableListOf()
    var transactions: MutableList<Transaction> = mutableListOf()
    var cryptocurrencies: MutableSet<Cryptocurrency>? = null

    fun registerUser(user: User) {
        validateUser(user)
        users.add(user)
    }

    fun getPrices(): List<PriceHistory> {
        val prices = mutableListOf<PriceHistory>()
        for (crypto in cryptocurrencies!!) {
            crypto.lastPrice()?.let { prices.add(it) }
        }
        return prices
    }

    fun currencyFluctuation(cryptocurrency: Cryptocurrency): List<PriceHistory> {
        return cryptocurrency.pricesOver24hs()
    }

    fun publishOrder(order: Order) {
        isUserRegistered(order.ownerUser!!)
        validatePriceMargin(order)
        orders.add(order)
    }

    private fun validatePriceMargin(order: Order) {
        var lastPrice = order.cryptocurrency!!.lastPrice()!!.price
        var userPrice = order.price
        var margin = lastPrice?.times(0.05)
        if (userPrice!! > lastPrice!! + margin!! || userPrice < lastPrice - margin) {
            throw Exception("Price is out of margin range of 5% of the last price of the cryptocurrency")
        }
    }

    fun active0rders(): List<Order> {
        return orders.filter { it.state == StateOrder.OPEN }
    }

    fun active0rdersByUser(user: User): List<Order> {
        isUserRegistered(user)
        return orders.filter { it.ownerUser == user && it.state == StateOrder.OPEN }
    }

    fun startTransaction(order: Order, counterParty: User): Transaction {
        isUserRegistered(counterParty)
        isRegisteredOrder(order)
        areSameUsers(order.ownerUser!!, counterParty)
        order.isAvailable()
        var transaction = TransactionBuilder().withOrder(order).withCounterParty(counterParty).build()
        transactions.add(transaction)
        return transaction
    }

    private fun areSameUsers(ownerUser: User, counterParty: User) {
        if(counterParty==ownerUser){
            throw Exception("The buyer and the seller are the same person")
        }
    }

    private fun validateUser(user: User) {
        if (users.any { it.email == user.email }) {
            throw IllegalArgumentException("Email already exists")
        }
    }

    private fun isUserRegistered(ownerUser: User) {
        if (!users.contains(ownerUser)) {
            throw IllegalArgumentException("User is not registered")
        }
    }

    private fun isRegisteredOrder(order: Order) {
        if (!orders.contains(order)) {
            throw IllegalArgumentException("Order is not registered")
        }
    }



}