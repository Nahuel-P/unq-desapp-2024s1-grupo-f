package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.state.PendingState
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.state.TransactionState
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @ManyToOne
    var buyer : User? = null

    @ManyToOne
    var seller : User? = null

    @ManyToOne
    var order: Order? = null

    @Embedded
    var state: TransactionState = PendingState()

    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null
    var isActive: Boolean = false
}