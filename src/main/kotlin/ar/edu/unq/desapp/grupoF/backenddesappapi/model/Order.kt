package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "Orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    var ownerUser: User? = null

    @ManyToOne
    var cryptocurrency: Cryptocurrency? = null

    var amount: Double? = null
    var price: Double? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "intention_type")
    var type: IntentionType? = null

    var entryTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "state_order")
    @Enumerated(EnumType.STRING)
    var state: StateOrder = StateOrder.OPEN

    var priceARS: Double? = 0.00

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    fun isTransferable(): Boolean {
        return (state == StateOrder.OPEN && isActive)
    }

    fun disable() {
        this.isActive = false
    }

    fun reset() {
        this.state = StateOrder.OPEN
        this.isActive = true
    }

    fun close() {
        this.state = StateOrder.CLOSED
        this.isActive = false
    }

    fun isFinished(): Boolean {
        return (state == StateOrder.CLOSED && !isActive)
    }

    fun isBuyOrder(): Boolean {
        return type == IntentionType.BUY
    }

    fun isSellOrder(): Boolean {
        return type == IntentionType.SELL
    }

    fun isAboveMarketPrice(crypto: Cryptocurrency, order: Order): Boolean {
        return crypto.lastPrice > order.price!!
    }

    fun isBelowMarketPrice(crypto: Cryptocurrency, order: Order): Boolean {
        return crypto.lastPrice < order.price!!
    }

    fun isAboveMarginPrice(marginPercentage: Double): Boolean {
        val marginFactor = 1 + marginPercentage / 100
        val upperBound = cryptocurrency!!.lastPrice()!! * marginFactor
        return this.price!! >= upperBound
    }

    fun isBelowMarginPrice(marginPercentage: Double): Boolean {
        val marginFactor = 1 - marginPercentage / 100
        val lowerBound = cryptocurrency!!.lastPrice()!! * marginFactor
        return this.price!! <= lowerBound
    }

    fun calculateArsPrice(arsRateBuy: Double, arsRateSell: Double) {
        this.priceARS = if (this.isBuyOrder()) {
            (this.amount!! * this.price!!) * arsRateBuy
        } else {
            (this.amount!! * this.price!!) * arsRateSell
        }
    }
}