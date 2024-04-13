package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.state.PaymentPendingState
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.state.TransactionState
import jakarta.persistence.*
import java.time.LocalDateTime

class Transaction {
    var id: Int? = null

    var buyer : User? = null

    var seller : User? = null

    var order: Order? = null

    var state: TransactionState = PaymentPendingState()

    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null
    var isActive: Boolean = false
}