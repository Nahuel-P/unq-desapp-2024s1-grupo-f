package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.StateOrder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime


class TransactionTest {

    private var buyer = aUser().build()
    private var seller = anotherUser().build()
    private var buyOrder = aBuyOrder().build()
    private var sellOrder = aSellOrder().build()

    @Test
    fun `should create a purchase transaction when it has valid data`() {
        assertDoesNotThrow { TransactionBuilder().build() }
    }

    @Test
    fun `should create a sale transaction when it has valid data`() {
        val order = aBuyOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertDoesNotThrow {
            TransactionBuilder()
                .withOrder(order)
                .withCounterParty(buyer)
                .build()
        }
    }

    @Test
    fun `should create a transaction when the buyer and seller are different`() {
        val order = aBuyOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertDoesNotThrow {
            TransactionBuilder()
                .withOrder(order)
                .withCounterParty(buyer)
                .build()
        }
    }

    @Test
    fun `should set status to PAID when paid is called`() {
        val order = aBuyOrder().withType(IntentionType.BUY).withOwnerUser(buyer).build()
        val transaction = TransactionBuilder().withOrder(order).withCounterParty(seller).build()
        transaction.paid()
        val status = transaction.status
        assertEquals(TransactionStatus.PAID, status)
    }

    @Test
    fun `should set status to PAID and paidTime when paid is called`() {
        val transaction = Transaction()
        transaction.paid()
        assertEquals(TransactionStatus.PAID, transaction.status)
        assertNotNull(transaction.paidTime)
    }

    @Test
    fun `should set status to CANCELLED_BY_USER and isActive to false when cancelByUser is called`() {
        val transaction = TransactionBuilder().withOrder(buyOrder).build()
        transaction.cancelByUser()
        assertEquals(TransactionStatus.CANCELLED_BY_USER, transaction.status)
    }

    @Test
    fun `should set status to CANCELLED_BY_SYSTEM and isActive to false when cancelBySystem is called`() {
        val transaction = TransactionBuilder().withOrder(buyOrder).build()
        transaction.cancelBySystem()
        assertEquals(TransactionStatus.CANCELLED_BY_SYSTEM, transaction.status)
    }

    @Test
    fun `should set status to CONFIRMED, isActive to false and close the order when confirmed is called`() {
        val transaction = TransactionBuilder().withOrder(buyOrder).build()
        transaction.confirmed()
        assertEquals(TransactionStatus.CONFIRMED, transaction.status)
        assertEquals(StateOrder.CLOSED, transaction.order!!.state)
    }

    @Test
    fun `should set endTime when cancelByUser is called`() {
        val transaction = TransactionBuilder().withOrder(buyOrder).build()
        transaction.cancelByUser()
        assertDoesNotThrow { transaction.endTime }
    }

    @Test
    fun `should set endTime when cancelBySystem is called`() {
        val transaction = TransactionBuilder().withOrder(buyOrder).build()
        transaction.cancelBySystem()
        assertDoesNotThrow { transaction.endTime }
    }

    @Test
    fun `the seller should be the owner of the order when the order is a sale`() {
        val order = aBuyOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        val transaction = TransactionBuilder().withOrder(order).withCounterParty(buyer).build()
        assertEquals(seller, transaction.seller())
    }

    @Test
    fun `the seller should be the counterParty when the order is a purchase`() {
        val order = aBuyOrder().withType(IntentionType.BUY).withOwnerUser(buyer).build()
        val transaction = TransactionBuilder().withOrder(order).withCounterParty(seller).build()
        assertEquals(seller, transaction.seller())
    }

    @Test
    fun `the buyer should be the owner of the order when the order is a purchase`() {
        val order = aBuyOrder().withType(IntentionType.BUY).withOwnerUser(buyer).build()
        val transaction = TransactionBuilder().withOrder(order).withCounterParty(seller).build()
        assertEquals(buyer, transaction.buyer())
    }

    @Test
    fun `the buyer should be the counterParty when the order is a sale`() {
        val order = aBuyOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        val transaction = TransactionBuilder().withOrder(order).withCounterParty(buyer).build()
        assertEquals(buyer, transaction.buyer())
    }


    @Test
    fun `should throw exception when scoreBasedOnTimeLapse is called before transaction end`() {
        val transaction = Transaction()
        assertThrows<Exception> { transaction.scoreBasedOnTimeLapse() }
    }

    @Test
    fun `should return correct elapsed minutes since creation`() {
        val transaction = Transaction()
        transaction.entryTime = LocalDateTime.now().minusMinutes(10)
        assertEquals(10.0, transaction.elapsedMinutesCreation())
    }

    @Test
    fun `should return correct elapsed minutes since paid`() {
        val transaction = Transaction()
        transaction.paidTime = LocalDateTime.now().minusMinutes(5)
        assertEquals(5.0, transaction.elapsedMinutesPaid())
    }

}