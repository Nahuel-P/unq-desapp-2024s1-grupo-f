package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

class TransactionBuilder {
//    private var id: Long? = null
    private var order: Order? = null
    private var counterParty: User? = null
//    private var status: TransactionStatus? = TransactionStatus.PENDING
//    private var entryTime: LocalDateTime = LocalDateTime.now()
//    private var endTime: LocalDateTime? = null
//    private var isActive: Boolean = false

    fun build(): Transaction {
        var transaction = Transaction()
        transaction.order = this.order
        transaction.counterParty = this.counterParty
        return transaction
    }

    fun withCounterParty(counterParty: User): TransactionBuilder {
        this.counterParty = counterParty
        return this
    }

    fun withOrder(order: Order): TransactionBuilder {
        this.order = order
        return this
    }

//    fun withStatus(status: TransactionStatus): TransactionBuilder {
//        this.status = status
//        return this
//    }
//
//    fun withEntryTime(entryTime: LocalDateTime): TransactionBuilder {
//        this.entryTime = entryTime
//        return this
//    }
//
//    fun withEndTime(endTime: LocalDateTime): TransactionBuilder {
//        this.endTime = endTime
//        return this
//    }
//
//    fun withIsActive(isActive: Boolean): TransactionBuilder {
//        this.isActive = isActive
//        return this
//    }
//
//    fun withId(id: Long): TransactionBuilder {
//        this.id = id
//        return this
//    }

}