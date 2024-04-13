package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDateTime

class TransactionTest {

    @Test
    fun `transaction should have unique id after creation`() {
        val transaction1 = mock(Transaction::class.java)
        val transaction2 = mock(Transaction::class.java)
        `when`(transaction1.id).thenReturn(1)
        `when`(transaction2.id).thenReturn(2)
        assertNotEquals(transaction1.id, transaction2.id)
    }

    @Test
    fun `transaction should have entry time after creation`() {
        val transaction = Transaction()
        assertNotNull(transaction.entryTime)
    }

    @Test
    fun `transaction should be pending after creation`() {
        val transaction = Transaction()
        val state = transaction.state
        assertEquals("PAYMENT_PENDING", state.getState())
    }

    @Test
    fun `transaction should be active after activation`() {
        val transaction = Transaction()
        transaction.isActive = true
        assertTrue(transaction.isActive)
    }

    @Test
    fun `transaction should have end time after completion`() {
        val transaction = Transaction()
        transaction.endTime = LocalDateTime.now()
        assertNotNull(transaction.endTime)
    }

}