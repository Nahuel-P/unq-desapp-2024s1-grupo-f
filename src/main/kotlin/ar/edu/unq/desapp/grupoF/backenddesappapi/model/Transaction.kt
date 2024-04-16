package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

class Transaction {
//    var cryptocurrency: Cryptocurrency? = null
//    var quantity: Double = 0.0
//    var price: Double = 0.0
//    var buyer : User? = null
//    var seller : User? = null
    var order: Order? = null
    var counterParty: User? = null
    var status: TransactionStatus? = TransactionStatus.PENDING
    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null

//    fun startTransaction(order: Order, counterParty: User): Transaction{
//        if(counterParty==order.ownerUser){
//            throw Exception("The buyer and the seller are the same person")
//        }
//
//        val transaction = Transaction()
//        transaction.cryptocurrency = order.cryptocurrency
//        transaction.price = order.price!!
//        transaction.quantity = order.amount!!
//        transaction.buyer = buyer
//        transaction.seller = seller
//        transaction.order = order
//        transaction.status = TransactionStatus.PENDING
//        transaction.entryTime = LocalDateTime.now()
//        transaction.isActive = true
//        transaction.order!!.close()
//
//
//
//        if(order.type== IntentionType.BUY) {
//            transaction.buyer = order.ownerUser
//            transaction.seller = counterParty
//        }
//        if(order.type== IntentionType.SELL) {
//            transaction.seller = order.ownerUser
//            transaction.buyer = counterParty
//        }
//
//        if(order.type == IntentionType.SELL && order.ownerUser != transaction.seller){
//            throw Exception("The seller is not the owner of the order")
//        }
//        if(order.type == IntentionType.BUY && order.ownerUser != transaction.buyer){
//            throw Exception("The buyer is not the owner of the order")
//        }
//        return transaction
//    }



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

    fun cancelBySystem(): Transaction{
        this.status = TransactionStatus.CANCELLED_BY_SYSTEM
        this.endTime = LocalDateTime.now()
        this.order!!.reset()
        return this
    }

    fun confirmed(): Transaction{
        this.status = TransactionStatus.CONFIRMED
        this.endTime = LocalDateTime.now()
        this.order!!.close()
        return this
    }

    fun seller(): User? {
        return if (order!!.type == IntentionType.SELL ){
            order!!.ownerUser
        } else {
            counterParty
        }
    }

    fun buyer(): User? {
        return if (order!!.type == IntentionType.BUY ){
            order!!.ownerUser
        } else {
            counterParty
        }
    }
}