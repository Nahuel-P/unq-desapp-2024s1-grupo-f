package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.ExchangeSystemBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ExchangeSystemTest {

    fun aCrypto(): Cryptocurrency {
        return CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
    }

    fun aUser(): User {
        return UserBuilder().build()
    }

    fun aCryptocurrencySet(): MutableSet<Cryptocurrency> {
        return mutableSetOf(aCrypto())
    }

    fun aOrder(): OrderBuilder {
        return OrderBuilder()
            .withOwnerUser(aUser())
            .withCryptocurrency(aCrypto())
            .withAmount(10.0)
            .withPrice(50000.0)
            .withType(IntentionType.BUY)
    }

    fun aExchangeSystem(): ExchangeSystemBuilder {
        return ExchangeSystemBuilder().
                withCryptocurrencies(aCryptocurrencySet())

    }

    @Test
    fun `should create a exchange system when it has valid data`() {
        assertDoesNotThrow { aExchangeSystem().build() }
    }

    @Test
    fun `should add user to users set when email is unique`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = UserBuilder().withAddress("testmail@hotmail.com").build()
        exchangeSystem.registerUser(user)
        assertTrue(exchangeSystem.users.contains(user))
    }

    @Test
    fun `should not register two users with the same email`() {
        val exchangeSystem = aExchangeSystem().build()
        val aUser = UserBuilder().withEmail("testmail@hotmail.com").build()
        val aMirrorUser = UserBuilder().withEmail("testmail@hotmail.com").build()
        exchangeSystem.registerUser(aUser)
        val exception = assertThrows<IllegalArgumentException> {
            exchangeSystem.registerUser(aMirrorUser)
        }
        assertEquals("Email already exists", exception.message)
    }

    @Test
    fun `should place an order from a user`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)
        val order = aOrder().withOwnerUser(user).build()
        exchangeSystem.publishOrder(order)
        assertTrue(exchangeSystem.orders.contains(order))
    }

    @Test
    fun `cannot place an order from an unregistered user`() {
        val exchangeSystem = aExchangeSystem().build()
        val order = aOrder().build()
        val exception = assertThrows<IllegalArgumentException> {
            exchangeSystem.publishOrder(order)
        }
        assertEquals("User is not registered", exception.message)
    }
}