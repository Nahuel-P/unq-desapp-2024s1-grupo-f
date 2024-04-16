package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.Duration
import java.time.LocalDateTime

class ExchangeSystem {

    var users: MutableSet<User> = mutableSetOf()
    var orders: MutableList<Order> = mutableListOf()
    var transactions: MutableList<Transaction> = mutableListOf()
    var cryptocurrencies: MutableSet<Cryptocurrency>? = null
    var USTPrice: Double = 00.00

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

    fun publishOrder(user: User, cryptocurrency: Cryptocurrency, amount: Double, price: Double, type: IntentionType): Order {
        isUserRegistered(user)
        val order = OrderBuilder()
            .withOwnerUser(user)
            .withCryptocurrency(cryptocurrency)
            .withAmount(amount)
            .withPrice(price)
            .withType(type)
            .withPriceARS(amount * price * USTPrice)
            .build()
        validatePriceMargin(order)
        orders.add(order)
        return order
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
        var transaction = TransactionBuilder()
            .withOrder(order)
            .withCounterParty(counterParty)
            .build()
        transactions.add(transaction)
        order.disable()
        return transaction
    }

    fun buyerPaidTransaction(transaction: Transaction, user: User): Transaction {
        existTransaction(transaction)
        areSameUsers(transaction.buyer()!!, user)
        isAvaibleToPaid(transaction)
        return transaction.paid()
    }

    fun buyerCancelTransaction(transaction: Transaction, user: User): Transaction {
        existTransaction(transaction)
        areSameUsers(transaction.buyer()!!,user)
        var updateTransaction = transaction.cancelByUser()
        user.decreaseScore()
        return updateTransaction
    }

    fun sellerConfirmTransaction(transaction: Transaction, user: User): Transaction {
        existTransaction(transaction)
        areSameUsers(transaction.seller()!!,user)
        isAvaibleToConfirmed(transaction)
        var updateTransaction = transaction.confirmed()
        updateReputationBy(transaction.entryTime, transaction.endTime,transaction.buyer()!!, transaction.seller()!!)
        return updateTransaction
    }

    private fun updateReputationBy(entryTime: LocalDateTime, endTime: LocalDateTime?, buyer: User, seller: User) {
        var duration = Duration.between(entryTime, endTime)
        var increment = 10
        if (duration.toMinutes() > 30) {
            increment = 5
        }
        buyer.increaseScore(increment).increaseTransactions()
        seller.increaseScore(increment).increaseTransactions()
    }

    fun sellerCancelTransaction(transaction: Transaction, user: User) {
        existTransaction(transaction)
        areSameUsers(transaction.seller()!!,user)
        isAvaibleToConfirmed(transaction)
        transaction.cancelByUser()
        user.decreaseScore()
    }

    private fun isAvaibleToConfirmed(transaction: Transaction) {
        if (transaction.status != TransactionStatus.PAID) {
            throw IllegalArgumentException("Transaction is not available to be paid for the buyer")
        }
    }

    private fun isAvaibleToPaid(transaction: Transaction) {
        if (transaction.status != TransactionStatus.PENDING) {
            throw IllegalArgumentException("Transaction is not available to be paid for the buyer")
        }
    }

    private fun existTransaction(transaction: Transaction) {
        if (transactions.contains(transaction)) {
            throw IllegalArgumentException("Transaction is not registered")
        }
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