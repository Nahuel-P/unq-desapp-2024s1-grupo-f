package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime


class TransactionTest {
    private var buyer = aBuyer().build()
    private var seller = aSeller().build()
    private fun aBuyer(): UserBuilder {
        return UserBuilder()
            .withFirstName("Miguel Angel")
            .withLastName("Borja")
            .withEmail("el.colibri09@gmail.com")
            .withAddress("Av. Siempre Viva 742")
            .withPassword("Contraseña1!")
            .withCvu("1234567890123456789012")
            .withWalletAddress("12345678")
    }


    private fun aSeller(): UserBuilder {
        return UserBuilder()
            .withFirstName("Joseph")
            .withLastName("Perez")
            .withEmail("josephperez@gmail.com")
            .withAddress("Av. Siempre Viva 740")
            .withPassword("Contraseña2!")
            .withCvu("1234567890123456789013")
            .withWalletAddress("12345679")
    }

    private fun aTransaction(): TransactionBuilder {
        return TransactionBuilder()
            .withCryptocurrency(aCryptoCurrency().build())
            .withQuantity(10.0)
            .withPrice(1000.0)
            .withTotalAmount(10000.0)
            .withBuyer(buyer)
            .withSeller(seller)
            .withOrder(anOrder().build())
            .withStatus(TransactionStatus.PENDING)
            .withEntryTime(LocalDateTime.now())
            .withEndTime(LocalDateTime.now())
            .withIsActive(true)
    }

    private fun aCryptoCurrency(): CryptocurrencyBuilder {
        val now = LocalDateTime.now()
        return CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withCreated(now)
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
                            .withSeller(seller)
                            .withBuyer(buyer)
                            .build() }
    }

    @Test
    fun `should create a transaction when the buyer and seller are different`() {
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertDoesNotThrow { aTransaction()
                            .withOrder(order)
                            .withSeller(seller)
                            .withBuyer(buyer)
                            .build() }
    }


    @Test
    fun `should throw an error when the buyer is the same as the seller`() {
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        val exception = assertThrows<Exception> {
            aTransaction()
                .withOrder(order)
                .withSeller(buyer)
                .withBuyer(buyer)
                .build()
        }
        assertEquals("The buyer and the seller are the same person", exception.message)
    }

    @Test
    fun `should throw an error when the user or buyer is null`(){
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertThrows<java.lang.Exception>() {
            aTransaction()
                .withOrder(order)
                .withSeller(seller)
                .withBuyer(null)
                .build()
        }
    }

    @Test
    fun `should throw an error when the user or seller is null`(){
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(seller).build()
        assertThrows<java.lang.Exception>() {
            aTransaction()
                .withOrder(order)
                .withSeller(null)
                .withBuyer(buyer)
                .build()
        }
    }

    @Test
    fun `should throw an error when the order is null`(){
        assertThrows<java.lang.Exception>() {
            aTransaction()
                .withOrder(null)
                .withSeller(seller)
                .withBuyer(buyer)
                .build()
        }
    }

    @Test
    fun `should throw an error when the seller is not the owner of the order`(){
        val order = anOrder().withType(IntentionType.SELL).withOwnerUser(buyer).build()
        val exception = assertThrows<Exception> {
            aTransaction()
                .withOrder(order)
                .withSeller(seller)
                .withBuyer(buyer)
                .build()
        }
        assertEquals("The seller is not the owner of the order", exception.message)
    }

    @Test
    fun `should throw an error when the buyer is not the owner of the order`(){
        val order = anOrder().withType(IntentionType.BUY).withOwnerUser(seller).build()
        val exception = assertThrows<Exception> {
            aTransaction()
                .withOrder(order)
                .withSeller(seller)
                .withBuyer(buyer)
                .build()
        }
        assertEquals("The buyer is not the owner of the order", exception.message)
    }

}