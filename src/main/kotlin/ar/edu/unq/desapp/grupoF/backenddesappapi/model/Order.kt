package ar.edu.unq.desapp.grupoF.backenddesappapi.model
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import java.time.LocalDateTime

class Order {
    var ownerUser: User? = null
    var cryptocurrency: Cryptocurrency? = null
    var amount: Double? = null
    var price: Double? = null
    var type: IntentionType? = null
    var entryTime: LocalDateTime = LocalDateTime.now()
    var state: StateOrder = StateOrder.OPEN
    var priceARS: Double? = 0.00
    private var isActive: Boolean = true

    fun close(){
        this.state = StateOrder.CLOSED
        this.isActive = false
    }

    fun isTransactable(): Boolean {
        return (state != StateOrder.OPEN && isActive)
    }

    fun isBuyOrder(): Boolean {
        return type == IntentionType.BUY
    }

    fun isSellOrder(): Boolean {
        return type == IntentionType.SELL
    }

    fun isActive(): Boolean {
        return (state == StateOrder.OPEN && isActive)
    }

    fun isFinished(): Boolean {
        return (state == StateOrder.CLOSED && !isActive)
    }

    fun disable() {
        this.isActive = false
    }

    fun reset() {
        this.state = StateOrder.OPEN
        this.isActive = true
    }
}