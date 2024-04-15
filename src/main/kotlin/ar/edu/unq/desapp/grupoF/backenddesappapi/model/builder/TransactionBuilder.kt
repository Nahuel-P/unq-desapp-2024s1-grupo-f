package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

class TransactionBuilder {
    private var id: Long? = null
    private var cryptocurrency: Cryptocurrency? = null
    private var quantity: Double? = 0.0
    private var price: Double? = 0.0
    private var totalAmount: Double? = 0.0
    private var buyer : User? = null
    private var seller : User? = null
    private var order: Order? = null
    private var status: TransactionStatus? = TransactionStatus.PENDING
    private var entryTime: LocalDateTime = LocalDateTime.now()
    private var endTime: LocalDateTime? = null
    private var isActive: Boolean = false

    fun build(): Transaction {
        return Transaction().startTransaction(
        cryptocurrency!!,
        quantity!!,
        price!!,
        totalAmount!!,
        buyer!!,
        seller!!,
        order!!
        )
}

        fun withCryptocurrency(cryptocurrency: Cryptocurrency): TransactionBuilder {
            this.cryptocurrency = cryptocurrency
            return this
        }

        fun withQuantity(quantity: Double): TransactionBuilder {
            this.quantity = quantity
            return this
        }

        fun withPrice(price: Double): TransactionBuilder {
            this.price = price
            return this
        }

        fun withTotalAmount(totalAmount: Double): TransactionBuilder {
            this.totalAmount = totalAmount
            return this
        }

        fun withBuyer(buyer: User?): TransactionBuilder {
            this.buyer = buyer
            return this
        }

        fun withSeller(seller: User?): TransactionBuilder {
            this.seller = seller
            return this
        }

        fun withOrder(order: Order?): TransactionBuilder {
            this.order = order
            return this
        }

        fun withStatus(status: TransactionStatus): TransactionBuilder {
            this.status = status
            return this
        }

        fun withEntryTime(entryTime: LocalDateTime): TransactionBuilder {
            this.entryTime = entryTime
            return this
        }

        fun withEndTime(endTime: LocalDateTime): TransactionBuilder {
            this.endTime = endTime
            return this
        }

        fun withIsActive(isActive: Boolean): TransactionBuilder {
            this.isActive = isActive
            return this
        }

        fun withId(id: Long): TransactionBuilder {
            this.id = id
            return this
        }

}