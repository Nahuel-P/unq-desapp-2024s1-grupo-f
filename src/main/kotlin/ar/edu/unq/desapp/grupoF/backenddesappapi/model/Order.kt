package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import java.time.LocalDateTime

class Order {
    var ownerUser: User? = null
    var cryptocurrency: Cryptocurrency? = null
    var amount: Double? = null
    var price: Double? = null   //validar que este +-5% del precio actual
    var type: IntentionType? = null
    var entryTime: LocalDateTime = LocalDateTime.now()
    var isActive: Boolean = true
    var state: StateOrder = StateOrder.OPEN
//    var priceARS: Double? = null

    fun close(){
        this.isActive = false
        this.state = StateOrder.CLOSED
    }

    fun isAvailable() {
        if (state != StateOrder.OPEN && !isActive) {
            throw IllegalArgumentException("The order is not available to start a transaction")
        }
    }

    fun disable() {
        this.isActive = false
    }

    fun reset() {
        this.state = StateOrder.OPEN
        this.isActive = true
    }
}