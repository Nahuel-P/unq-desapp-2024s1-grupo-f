package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime

class TransactionMapperTest {

    @Test
    fun `toModel should return a Transaction with the same order and counterParty as the input`() {
        val dto = Mockito.mock(TransactionCreateDTO::class.java)
        val user = Mockito.mock(User::class.java)
        val order = Mockito.mock(Order::class.java)

        val result = TransactionMapper.toModel(dto, user, order)

        assertEquals(user, result.counterParty)
        assertEquals(order, result.order)
    }

    @Test
    fun `toResponseDTO should return a TransactionResponseDTO with the same counterParty, order, status and entryTime as the input Transaction`() {
        val transaction = Mockito.mock(Transaction::class.java)
        val order = Mockito.mock(Order::class.java)
        val user = Mockito.mock(User::class.java)
        val status = TransactionStatus.PENDING
        val entryTime = LocalDateTime.now()

        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
        Mockito.`when`(order.cryptocurrency).thenReturn(cryptocurrency)
        Mockito.`when`(user.firstName).thenReturn("Test")
        Mockito.`when`(user.lastName).thenReturn("User")
        Mockito.`when`(user.email).thenReturn("test@gmail.com")
        Mockito.`when`(user.reputation()).thenReturn("0")
        Mockito.`when`(user.id).thenReturn(1L)
        Mockito.`when`(transaction.counterParty).thenReturn(user)

        Mockito.`when`(order.id).thenReturn(1L)
        Mockito.`when`(order.cryptocurrency).thenReturn(cryptocurrency)
        Mockito.`when`(order.amount).thenReturn(1.0)
        Mockito.`when`(order.price).thenReturn(1.0)
        Mockito.`when`(order.type).thenReturn(IntentionType.BUY)
        Mockito.`when`(order.priceARS).thenReturn(1.0)
        Mockito.`when`(order.ownerUser).thenReturn(user)

        Mockito.`when`(transaction.order).thenReturn(order)
        Mockito.`when`(transaction.status).thenReturn(status)
        Mockito.`when`(transaction.entryTime).thenReturn(entryTime)

        val result = TransactionMapper.toResponseDTO(transaction)

        assertEquals(status, result.status)
        assertEquals(entryTime, result.entryTime)
    }
}