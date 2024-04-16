package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import java.time.LocalDateTime

class OrderBuilder {
    private var ownerUser: User? = null
    private var cryptocurrency: Cryptocurrency? = null
    private var amount: Double? = null
    private var price: Double? = null
    private var type: IntentionType? = null
    private var entryTime: LocalDateTime = LocalDateTime.now()
    private var isActive: Boolean? = null
    private var state: StateOrder? = null

    fun build(): Order {
        requireNotNull(this.ownerUser) { "Owner user must not be null" }
        requireNotNull(this.cryptocurrency) { "Cryptocurrency must not be null" }
        requireNotNull(this.amount) { "Amount must not be null" }
        requireNotNull(this.price) { "Price must not be null" }
        requireNotNull(this.type) { "Type must not be null" }

        val order = Order()
        order.ownerUser = this.ownerUser
        order.cryptocurrency = this.cryptocurrency
        order.amount = this.amount
        order.price = this.price
        order.type = this.type
        order.entryTime = this.entryTime
        return order
    }

    fun withOwnerUser(ownerUser: User): OrderBuilder {
        this.ownerUser = ownerUser
        return this
    }

    fun withCryptocurrency(cryptocurrency: Cryptocurrency): OrderBuilder {
        this.cryptocurrency = cryptocurrency
        return this
    }

    fun withAmount(amount: Double): OrderBuilder {
        this.amount = amount
        return this
    }

    fun withPrice(price: Double): OrderBuilder {
        this.price = validatePriceMargin(price)
        return this
    }

    fun withType(type: IntentionType): OrderBuilder {
        this.type = type
        return this
    }

    private fun validatePriceMargin(price: Double): Double {
        val lastPrice = this.cryptocurrency?.lastPrice()?.price
        val margin = lastPrice?.times(0.05)
//        if (lastPrice!! > price + margin!! || lastPrice < price - margin) {
//            throw IllegalArgumentException("Price is out of margin range of 5% of the last price of the cryptocurrency")
//        }
        return price
    }

//    fun withEntryTime(entryTime: LocalDateTime): OrderBuilder {
//        this.entryTime = entryTime
//        return this
//    }
//
//    fun withIsActive(isActive: Boolean): OrderBuilder {
//        this.isActive = isActive
//        return this
//    }
//
//    fun withState(state: StateOrder): OrderBuilder {
//        this.state = state
//        return this
//    }



}