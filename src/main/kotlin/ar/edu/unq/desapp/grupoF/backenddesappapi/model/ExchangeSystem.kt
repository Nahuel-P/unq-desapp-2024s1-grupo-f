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
    var USTPrice: Double = 1.00

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

    fun publishOrder(
        user: User,
        cryptocurrency: Cryptocurrency,
        amount: Double,
        price: Double,
        type: IntentionType,
    ): Order {
        isUserRegistered(user)
        val order = OrderBuilder()
            .withOwnerUser(user)
            .withCryptocurrency(cryptocurrency)
            .withAmount(amount)
            .withPrice(price)
            .withType(type)
//            .withPriceARS(amount * price * USTPrice)
            .build()
        validatePriceMargin(order)
        orders.add(order)
        return order
    }

    private fun validatePriceMargin(order: Order) {
        val lastPrice = order.cryptocurrency?.lastPrice()?.price
        val userPrice = order.price
        val margin = lastPrice?.times(0.05)

        if (lastPrice == null || userPrice == null || margin == null) {
            throw Exception("Price or last price or margin is null")
        } else if (userPrice > lastPrice + margin || userPrice < lastPrice - margin) {
            throw Exception("Price is out of margin range of 5% of the last price of the cryptocurrency")
        }
    }

    fun active0rders(): List<Order> {
        return orders.filter { it.isActive }
    }

    fun active0rdersByUser(user: User): List<Order> {
        isUserRegistered(user)
        return orders.filter { it.ownerUser == user && it.state == StateOrder.OPEN }
    }

    fun startTransaction(order: Order, counterParty: User): Transaction {
        isUserRegistered(counterParty)
        isRegisteredOrder(order)
        areSameUsers(order.ownerUser!!, counterParty)
        isTransactableOrder(order)
        val transaction = TransactionBuilder()
            .withOrder(order)
            .withCounterParty(counterParty)
            .build()
        transactions.add(transaction)
        order.disable()
        return transaction
    }

    fun buyerPaidTransaction(transaction: Transaction): Transaction {
        existsTransaction(transaction)
        isAvailableToPaid(transaction)
        return transaction.paid()
    }

    fun buyerCancelTransaction(transaction: Transaction): Transaction {
        existsTransaction(transaction)
        var updateTransaction = transaction.cancelByUser()
        val user = transaction.buyer()!!
        user.decreaseScore()
        return updateTransaction
    }

    fun sellerConfirmTransaction(transaction: Transaction): Transaction {
        existsTransaction(transaction)
        isAvailableToConfirmed(transaction)
        var updateTransaction = transaction.confirmed()
        updateReputationBy(transaction.entryTime, transaction.endTime, transaction.buyer()!!, transaction.seller()!!)
        return updateTransaction
    }

    private fun updateReputationBy(entryTime: LocalDateTime, endTime: LocalDateTime?, buyer: User, seller: User) {
        val increment = calculateScoreBasedOnTimeLapse(entryTime, endTime)
        buyer.increaseScore(increment).increaseTransactions()
        seller.increaseScore(increment).increaseTransactions()
    }

    private fun calculateScoreBasedOnTimeLapse(entryTime: LocalDateTime, endTime: LocalDateTime?): Int {
        val duration = Duration.between(entryTime, endTime)
        return if (duration.toMinutes() > 30) {
            5
        } else {
            10
        }
    }

    fun sellerCancelsTransaction(transaction: Transaction) {
        existsTransaction(transaction)
        val user = transaction.seller()!!
        transaction.cancelByUser()
        user.decreaseScore()
    }

    private fun isAvailableToConfirmed(transaction: Transaction) {
        require(transaction.status == TransactionStatus.PAID) { "Transaction is not available to be paid for the buyer" }
    }

    private fun isAvailableToPaid(transaction: Transaction) {
        require(transaction.status == TransactionStatus.PENDING) { "Transaction is not available to be paid for the buyer" }
    }

    private fun existsTransaction(transaction: Transaction): Boolean {
        return transactions.contains(transaction)
    }

    private fun areSameUsers(ownerUser: User, counterParty: User) {
        if (counterParty == ownerUser) {
            throw Exception("The buyer and the seller are the same person")
        }
    }

    private fun validateUser(user: User) {
        require(!(users.any { it.email == user.email })) { "Email already exists" }
    }

    private fun isUserRegistered(ownerUser: User) {
        require(users.contains(ownerUser)) { "User is not registered" }
    }

    private fun isRegisteredOrder(order: Order) {
        require(orders.contains(order)) { "Order is not registered" }
    }

    private fun isTransactableOrder(order: Order) {
        require(order.isTransactable()) { "Order is not transactable at the moment" }
    }

}