package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class TransactionTest {
    private var buyer = aBuyer().build()
    private var seller = aSeller().build()
    private fun aBuyer(): UserBuilder {
        return UserBuilder()
    }

    private fun aSeller(): UserBuilder {
        return UserBuilder()
    }

    private fun aTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withCounterParty(seller)
            .withOrder(anOrder().build())
    }

    private fun aCryptoCurrency(): CryptocurrencyBuilder {
        return CryptocurrencyBuilder()
    }

    private fun anOrder(): OrderBuilder {
        return OrderBuilder()
            .withOwnerUser(buyer)
            .withCryptocurrency(aCryptoCurrency().build())
            .withAmount(10.0)
            .withPrice(50000.0)
            .withType(IntentionType.BUY)
    }



    @Test
    fun `should create a purchase transaction when it has valid data`() {
        assertDoesNotThrow { aTransaction().build() }
    }

    @Test
    fun `should create a sale transaction when it has valid data`() {
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertDoesNotThrow { aTransaction()
                            .withOrder(order)
                            .withCounterParty(buyer)
                            .build() }
    }

    @Test
    fun `should create a transaction when the buyer and seller are different`() {
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertDoesNotThrow { aTransaction()
                            .withOrder(order)
                            .withCounterParty(buyer)
                            .build() }
    }


//    @Test
//    fun `should throw an error when the buyer is the same as the seller`() {
//        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
//        val exception = assertThrows<Exception> {
//            aTransaction()
//                .withOrder(order)
//                .withCounterParty(seller)
//                .build()
//        }
//        assertEquals("The buyer and the seller are the same person", exception.message)
//    }

//    @Test
//    fun `should throw an error when the user or buyer is null`(){
//        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
//        assertThrows<java.lang.Exception>() {
//            aTransaction()
//                .withOrder(order)
//                .build()
//        }
//    }


//    @Test
//    fun `should throw an error when the order is null`(){
//        assertThrows<java.lang.Exception>() {
//            aTransaction()
//                .build()
//        }
//    }


    @Test
    fun `should set status to PAID when paid is called`() {
        val order = anOrder().withType(IntentionType.BUY).withOwnerUser(buyer).build()
        val transaction = aTransaction().withOrder(order).withCounterParty(seller).build()
        transaction.paid()
        val status = transaction.status
        assertEquals(TransactionStatus.PAID, status)
    }

    @Test
    fun `should set status to CANCELLED_BY_USER and isActive to false when cancelByUser is called`() {
        val transaction = aTransaction().build()
        transaction.cancelByUser()
        assertEquals(TransactionStatus.CANCELLED_BY_USER, transaction.status)
    }

    @Test
    fun `should set status to CANCELLED_BY_SYSTEM and isActive to false when cancelBySystem is called`() {
        val transaction = aTransaction().build()
        transaction.cancelBySystem()
        assertEquals(TransactionStatus.CANCELLED_BY_SYSTEM, transaction.status)
    }

    @Test
    fun `should set status to CONFIRMED, isActive to false and close the order when confirmed is called`() {
        val order = anOrder().build()
        val transaction = aTransaction().withOrder(order).build()
        transaction.confirmed()
        assertEquals(TransactionStatus.CONFIRMED, transaction.status)
        assertEquals(StateOrder.CLOSED, transaction.order!!.state)
    }

    @Test
    fun `should set endTime when cancelByUser is called`() {
        val transaction = aTransaction().build()
        transaction.cancelByUser()
        assertDoesNotThrow { transaction.endTime }
    }

    @Test
    fun `should set endTime when cancelBySystem is called`() {
        val transaction = aTransaction().build()
        transaction.cancelBySystem()
        assertDoesNotThrow { transaction.endTime }
    }

    @Test
    fun `the seller should be the owner of the order when the order is a sale`() {
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        val transaction = aTransaction().withOrder(order).withCounterParty(buyer).build()
        assertEquals(seller, transaction.seller())
    }

    @Test
    fun `the seller should be the counterParty when the order is a purchase`() {
        val order = anOrder().withType(IntentionType.BUY).withOwnerUser(buyer).build()
        val transaction = aTransaction().withOrder(order).withCounterParty(seller).build()
        assertEquals(seller, transaction.seller())
    }

    @Test
    fun `the buyer should be the owner of the order when the order is a purchase`() {
        val order = anOrder().withType(IntentionType.BUY).withOwnerUser(buyer).build()
        val transaction = aTransaction().withOrder(order).withCounterParty(seller).build()
        assertEquals(buyer, transaction.buyer())
    }

    @Test
    fun `the buyer should be the counterParty when the order is a sale`() {
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        val transaction = aTransaction().withOrder(order).withCounterParty(buyer).build()
        assertEquals(buyer, transaction.buyer())
    }

}