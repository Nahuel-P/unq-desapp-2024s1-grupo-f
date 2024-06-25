package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class CommonServiceImplTest {

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var transactionRepository: TransactionRepository

    @MockBean
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    @Autowired
    private lateinit var commonService: CommonServiceImpl

    @Test
    fun `getUser returns user when user exists`() {
        val user = UserBuilder().build()
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val result = commonService.getUser(1L)

        assertEquals(user, result)
    }

    @Test
    fun `getUser throws exception when user does not exist`() {
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<Exception> { commonService.getUser(1L) }
    }

    @Test
    fun `getTransaction returns transaction when transaction exists`() {
        val transaction = TransactionBuilder().build()
        Mockito.`when`(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction))

        val result = commonService.getTransaction(1L)

        assertEquals(transaction, result)
    }

    @Test
    fun `getTransaction throws exception when transaction does not exist`() {
        Mockito.`when`(transactionRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<Exception> { commonService.getTransaction(1L) }
    }

    @Test
    fun `getCrypto returns cryptocurrency when cryptocurrency exists`() {
        val crypto = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
        Mockito.`when`(cryptocurrencyRepository.findByName(CryptoSymbol.BTCUSDT)).thenReturn(crypto)

        val result = commonService.getCrypto(CryptoSymbol.BTCUSDT)

        assertEquals(crypto, result)
    }

    @Test
    fun `getCrypto throws exception when cryptocurrency does not exist`() {
        Mockito.`when`(cryptocurrencyRepository.findByName(CryptoSymbol.BTCUSDT)).thenReturn(null)

        assertThrows<Exception> { commonService.getCrypto(CryptoSymbol.BTCUSDT) }
    }

    @Test
    fun `getTransactionBy returns transactions when transactions exist`() {
        val transactions = listOf(TransactionBuilder().build(), TransactionBuilder().build())
        val startDate = LocalDateTime.now().minusDays(1)
        val endDate = LocalDateTime.now()
        Mockito.`when`(transactionRepository.findCompletedTransactionsByUserAndBetweenDates(1L, startDate, endDate)).thenReturn(transactions)

        val result = commonService.getTransactionBy(1L, startDate, endDate)

        assertEquals(transactions, result)
    }

    @Test
    fun `getTransactionBy returns empty list when no transactions exist`() {
        val startDate = LocalDateTime.now().minusDays(1)
        val endDate = LocalDateTime.now()
        Mockito.`when`(transactionRepository.findCompletedTransactionsByUserAndBetweenDates(1L, startDate, endDate)).thenReturn(emptyList())

        val result = commonService.getTransactionBy(1L, startDate, endDate)

        assertTrue(result.isEmpty())
    }
}