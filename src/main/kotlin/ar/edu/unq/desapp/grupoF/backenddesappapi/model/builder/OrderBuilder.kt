package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import kotlin.math.pow

class OrderBuilder {
    private var ownerUser: User? = null
    private var cryptocurrency: Cryptocurrency? = null
    private var amount: Double? = null
    private var price: Double? = null
    private var type: IntentionType? = null
    private var priceARS: Double? = null


    fun build(): Order {
        val order = Order()
        order.ownerUser = this.ownerUser
        order.cryptocurrency = this.cryptocurrency
        order.amount = this.amount
        order.price = this.price
        order.type = this.type
        order.priceARS = this.priceARS
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

    fun withPriceARS(priceARS: Double): OrderBuilder {
        this.priceARS = priceARS
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

}