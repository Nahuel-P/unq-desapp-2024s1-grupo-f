package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.Duration
import java.time.LocalDateTime

@Entity(name = "transaction")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "The trade cannot be null.")
    var order: Order? = null

    @ManyToOne
    @JoinColumn(name = "counterParty_id", referencedColumnName = "id")
    var counterParty: User? = null

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The status cannot be null.")
    var status: TransactionStatus? = TransactionStatus.PENDING

    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null
    var paidTime: LocalDateTime? = null

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

    fun paid(): Transaction {
        this.status = TransactionStatus.PAID
        this.paidTime = LocalDateTime.now()
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

    fun scoreBasedOnTimeLapse(): Int {
        val endTime = this.endTime ?: throw Exception("Transaction has not ended yet")
        return if (endTime.isAfter(entryTime.plusMinutes(31))) 5 else 10
    }

    fun elapsedMinutesCreation(): Double {
        return Duration.between(entryTime, LocalDateTime.now()).toMinutes().toDouble()
    }

    fun elapsedMinutesPaid(): Double {
        return Duration.between(paidTime, LocalDateTime.now()).toMinutes().toDouble()
    }
}
