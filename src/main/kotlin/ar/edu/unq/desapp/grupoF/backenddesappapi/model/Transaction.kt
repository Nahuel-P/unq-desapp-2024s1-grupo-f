package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.state.PendingState
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.state.TransactionState
import jakarta.persistence.*
import java.time.LocalDateTime

class Transaction {
    var id: Int? = null

    var buyer : User? = null

    var seller : User? = null

    var order: Order? = null

    @Embedded
    var state: TransactionState = PendingState()

    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null
    var isActive: Boolean = false
}