package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import java.time.LocalDateTime

class Order {
    var ownerUser: User? = null
    var cryptocurrency: Cryptocurrency? = null
    var amount: Double? = null
    var price: Double? = null
    var totalAmount : Double? = null
    var type: IntentionType? = null
    var entryTime: LocalDateTime = LocalDateTime.now()
    var isActive: Boolean? = null
    var state: StateOrder? = null

    fun close(){
        this.isActive = false
        this.state = StateOrder.CLOSED
    }
}