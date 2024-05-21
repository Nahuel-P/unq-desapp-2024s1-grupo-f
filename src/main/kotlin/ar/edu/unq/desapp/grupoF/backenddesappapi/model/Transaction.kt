package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
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
    @JoinColumn(name = "counter_id", referencedColumnName = "id")
    var counterParty: User? = null

    @Column(nullable = false)
    @NotNull(message = "The status cannot be null.")
    var status: TransactionStatus? = TransactionStatus.PENDING

    @Column
    var entryTime: LocalDateTime = LocalDateTime.now()
    @Column
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
//        this.order!!.close()
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