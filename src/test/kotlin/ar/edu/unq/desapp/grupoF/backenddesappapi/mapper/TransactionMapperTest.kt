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
import java.time.format.DateTimeFormatter

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
    fun `toResponseDTO returns correct TransactionResponseDTO for buy order`() {
        val transaction = Mockito.mock(Transaction::class.java)
        val order = Mockito.mock(Order::class.java)
        val user = Mockito.mock(User::class.java)
        val crypto = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()

        Mockito.`when`(transaction.order).thenReturn(order)
        Mockito.`when`(order.isBuyOrder()).thenReturn(true)
        Mockito.`when`(order.ownerUser).thenReturn(user)
        Mockito.`when`(order.cryptocurrency).thenReturn(crypto)
        Mockito.`when`(order.type).thenReturn(IntentionType.BUY)
        Mockito.`when`(user.walletAddress).thenReturn("12345678")
        Mockito.`when`(user.id).thenReturn(1L)
        Mockito.`when`(user.firstName).thenReturn("michael")
        Mockito.`when`(user.lastName).thenReturn("scott")
        Mockito.`when`(user.email).thenReturn("prison.mike@gmail.com")
        Mockito.`when`(user.reputation()).thenReturn("5")
        Mockito.`when`(transaction.counterParty).thenReturn(user)
        Mockito.`when`(transaction.status).thenReturn(TransactionStatus.PENDING)
        Mockito.`when`(transaction.entryTime).thenReturn(LocalDateTime.now())

        val userDTO = UserMapper.toDTO(user)
        val result = TransactionMapper.toResponseDTO(transaction, "message")

        assertEquals(userDTO, result.userRequest)
        assertEquals(TransactionStatus.PENDING, result.status)
        assertEquals(transaction.entryTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), result.entryTime)
        assertEquals("", result.endTime)
        assertEquals("12345678", result.destination)
        assertEquals("message", result.message)
    }

    @Test
    fun `toResponseDTO returns correct TransactionResponseDTO for sell order`() {
        val transaction = Mockito.mock(Transaction::class.java)
        val order = Mockito.mock(Order::class.java)
        val user = Mockito.mock(User::class.java)
        val crypto = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()

        Mockito.`when`(transaction.order).thenReturn(order)
        Mockito.`when`(order.isBuyOrder()).thenReturn(false)
        Mockito.`when`(order.ownerUser).thenReturn(user)
        Mockito.`when`(order.cryptocurrency).thenReturn(crypto)
        Mockito.`when`(order.type).thenReturn(IntentionType.SELL)
        Mockito.`when`(user.cvu).thenReturn("1234567890123456789012")
        Mockito.`when`(user.id).thenReturn(1L)
        Mockito.`when`(user.firstName).thenReturn("michael")
        Mockito.`when`(user.lastName).thenReturn("scott")
        Mockito.`when`(user.email).thenReturn("prison.mike@gmail.com")
        Mockito.`when`(user.reputation()).thenReturn("5")
        Mockito.`when`(transaction.counterParty).thenReturn(user)
        Mockito.`when`(transaction.status).thenReturn(TransactionStatus.PENDING)
        Mockito.`when`(transaction.entryTime).thenReturn(LocalDateTime.now())

        val userDTO = UserMapper.toDTO(user)
        val result = TransactionMapper.toResponseDTO(transaction, "message")

        assertEquals(userDTO, result.userRequest)
        assertEquals(TransactionStatus.PENDING, result.status)
        assertEquals(transaction.entryTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), result.entryTime)
        assertEquals("", result.endTime)
        assertEquals("1234567890123456789012", result.destination)
        assertEquals("message", result.message)
    }
}