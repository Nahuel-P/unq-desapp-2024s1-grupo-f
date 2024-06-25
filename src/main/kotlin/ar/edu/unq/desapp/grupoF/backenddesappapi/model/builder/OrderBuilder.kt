package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType

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
        this.price = price
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

}