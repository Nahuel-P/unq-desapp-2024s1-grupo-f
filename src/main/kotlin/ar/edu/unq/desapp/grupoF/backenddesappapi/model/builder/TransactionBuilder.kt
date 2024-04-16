package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User

class TransactionBuilder {
    private var order: Order? = null
    private var counterParty: User? = null


    fun build(): Transaction {
        val transaction = Transaction()
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

}