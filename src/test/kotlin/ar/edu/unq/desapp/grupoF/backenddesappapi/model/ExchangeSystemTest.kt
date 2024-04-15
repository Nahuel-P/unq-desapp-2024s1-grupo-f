package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.ExchangeSystemBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class ExchangeSystemTest {

    fun aCrypto(): Cryptocurrency {
        return CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
    }

    fun aUser(): User {
        return UserBuilder().build()
    }

    fun aUser2(): User{
        return UserBuilder().withEmail("test@gmail.com").build()
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

    @Test
    fun `getPrices returns list of last prices for each cryptocurrency`() {
        val priceHistory1 = PriceHistory().apply {
            this.price = 100.0
            this.priceTime = LocalDateTime.now()
        }
        val crypto1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPriceHistory(mutableListOf(priceHistory1)).build()

        val priceHistory2 = PriceHistory().apply {
            this.price = 200.0
            this.priceTime = LocalDateTime.now()
        }
        val crypto2 = CryptocurrencyBuilder().withName(CryptoSymbol.ETHUSDT).withPriceHistory(mutableListOf(priceHistory2)).build()

        val exchangeSystem = aExchangeSystem().withCryptocurrencies(mutableSetOf(crypto1, crypto2)).build()

        val prices = exchangeSystem.getPrices(crypto1)

        assertEquals(2, prices.size)
    }

    @Test
    fun `getPrices returns empty list when there are no cryptocurrencies`() {
        val crypto1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
        val exchangeSystem = aExchangeSystem().build()

        val prices = exchangeSystem.getPrices(crypto1)

        assertTrue(prices.isEmpty())
    }

    @Test
    fun `currencyFluctuation returns list of prices from the last 24 hours`() {
        val crypto1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
        val exchangeSystem = aExchangeSystem().withCryptocurrencies(mutableSetOf(crypto1)).build()

        val prices = exchangeSystem.currencyFluctuation(crypto1)

        assertTrue(prices.all { it.priceTime.isAfter(LocalDateTime.now().minusDays(1)) })
    }

    @Test
    fun `currencyFluctuation returns empty list when there are no prices from the last 24 hours`() {
        val crypto1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
        val exchangeSystem = aExchangeSystem().withCryptocurrencies(mutableSetOf(crypto1)).build()

        val prices = exchangeSystem.currencyFluctuation(crypto1)

        assertTrue(prices.isEmpty())
    }

    @Test
    fun `ordersByUser returns orders owned by the user`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)
        val order1 = aOrder().withOwnerUser(user).build()
        val order2 = aOrder().withOwnerUser(user).build()
        exchangeSystem.publishOrder(order1)
        exchangeSystem.publishOrder(order2)

        val userOrders = exchangeSystem.ordersByUser(user)

        assertEquals(2, userOrders.size)
        assertTrue(userOrders.containsAll(listOf(order1, order2)))
    }

    @Test
    fun `ordersByUser returns empty list when user has no orders`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)

        val userOrders = exchangeSystem.ordersByUser(user)

        assertTrue(userOrders.isEmpty())
    }

    @Test
    fun `startTransaction creates a transaction for a registered user and order`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)
        val user2 = aUser2()
        exchangeSystem.registerUser(user2)
        val order = aOrder().withType(IntentionType.BUY).withOwnerUser(user).build()
        exchangeSystem.publishOrder(order)

        val transaction = exchangeSystem.startTransaction(order, user2)

        assertEquals(user, transaction.buyer)
        assertEquals(order, transaction.order)
        assertTrue(exchangeSystem.transactions.contains(transaction))
    }

    @Test
    fun `startTransaction throws IllegalArgumentException when user is not registered`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        val order = aOrder().withOwnerUser(user).build()

        assertThrows<IllegalArgumentException> {
            exchangeSystem.startTransaction(order, user)
        }
    }

    @Test
    fun `startTransaction throws IllegalArgumentException when order is not registered`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)
        val order = aOrder().withOwnerUser(user).build()

        assertThrows<IllegalArgumentException> {
            exchangeSystem.startTransaction(order, user)
        }
    }
}