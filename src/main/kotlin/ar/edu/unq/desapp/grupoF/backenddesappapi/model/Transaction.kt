package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

class Transaction {
    var id: Long? = null
    var cryptocurrency: Cryptocurrency? = null
    var quantity: Double = 0.0
    var price: Double = 0.0
    var totalAmount: Double = 0.0
    var buyer : User? = null
    var seller : User? = null
    var order: Order? = null
    var status: TransactionStatus? = TransactionStatus.PENDING
    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null
    var isActive: Boolean = false
    fun startTransaction(cryptocurrency: Cryptocurrency,
                        quantity: Double,
                        price: Double,
                        totalAmount: Double,
                        buyer: User,
                        seller: User,
                        order: Order): Transaction{
        if(seller==buyer){
            throw Exception("The buyer and the seller are the same person")
        }
        if(order.type== IntentionType.SELL && order.ownerUser != seller){
            throw Exception("The seller is not the owner of the order")
        }
        if(order.type== IntentionType.BUY && order.ownerUser != buyer){
            throw Exception("The buyer is not the owner of the order")
        }

        val transaction = Transaction()
        transaction.cryptocurrency = cryptocurrency
        transaction.price = price
        transaction.quantity = quantity
        transaction.totalAmount = totalAmount
        transaction.buyer = buyer
        transaction.seller = seller
        transaction.order = order
        transaction.status = TransactionStatus.PENDING
        transaction.entryTime = LocalDateTime.now()
        transaction.isActive = true
        transaction.order!!.close()

        // validation of buyer/seller for cvu or wallet
        return transaction
    }

    fun paid(){
        this.status = TransactionStatus.PAID
    }
    fun cancelByUser(){
        this.status = TransactionStatus.CANCELLED_BY_USER
        this.endTime = LocalDateTime.now()
        this.isActive = false
    }

    fun cancelBySystem(){
        this.status = TransactionStatus.CANCELLED_BY_SYSTEM
        this.endTime = LocalDateTime.now()
        this.isActive = false
    }
    fun confirmed(){
        this.status = TransactionStatus.CONFIRMED
        this.endTime = LocalDateTime.now()
        this.isActive = false
        this.order!!.close()
    }
}