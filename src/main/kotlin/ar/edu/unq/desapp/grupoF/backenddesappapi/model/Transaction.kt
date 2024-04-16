package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

class Transaction {

    var order: Order? = null
    var counterParty: User? = null
    var status: TransactionStatus? = TransactionStatus.PENDING
    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null


    fun paid(): Transaction {
        this.status = TransactionStatus.PAID
        return this
    }

    fun cancelByUser(): Transaction {
        this.status = TransactionStatus.CANCELLED_BY_USER
        this.endTime = LocalDateTime.now()
        this.order!!.reset()
        return this
    }

    fun cancelBySystem(): Transaction {
        this.status = TransactionStatus.CANCELLED_BY_SYSTEM
        this.endTime = LocalDateTime.now()
        this.order!!.reset()
        return this
    }

    fun confirmed(): Transaction {
        this.status = TransactionStatus.CONFIRMED
        this.endTime = LocalDateTime.now()
        this.order!!.close()
        return this
    }

    fun seller(): User? {
        return if (order!!.type == IntentionType.SELL) {
            order!!.ownerUser
        } else {
            counterParty
        }
    }

    fun buyer(): User? {
        return if (order!!.type == IntentionType.BUY) {
            order!!.ownerUser
        } else {
            counterParty
        }
    }
}