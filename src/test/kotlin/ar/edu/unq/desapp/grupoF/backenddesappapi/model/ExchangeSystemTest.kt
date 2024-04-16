package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.ExchangeSystemBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class ExchangeSystemTest {

    fun cryptoHistory(): MutableList<PriceHistory> {
        val priceHistory = PriceHistory(CryptoSymbol.BTCUSDT, 50000.0)
        return mutableListOf(priceHistory)
    }
    fun aCrypto(): Cryptocurrency {
        return CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPriceHistory(cryptoHistory()).build()
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

    private fun aExchangeSystem(): ExchangeSystemBuilder {
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

//    @Test
//    fun `should place an order from a user`() {
//        val exchangeSystem = aExchangeSystem().build()
//        val user = aUser()
//        exchangeSystem.registerUser(user)
//        val order = aOrder().withOwnerUser(user).build()
//        exchangeSystem.publishOrder(order)
//        assertTrue(exchangeSystem.orders.contains(order))
//    }

    @Test
    fun `cannot place an order from an unregistered user`() {
        val exchangeSystem = aExchangeSystem().build()
        val exception = assertThrows<IllegalArgumentException> {
            exchangeSystem.publishOrder(aUser(), aCrypto(), 1.0, 1.0, IntentionType.SELL)
        }
        assertEquals("User is not registered", exception.message)
    }

    @Test
    fun `getPrices returns list of last prices for each cryptocurrency`() {
        val priceHistory1 = PriceHistory(CryptoSymbol.BTCUSDT, 2.0).apply {
            this.price = 100.0
            this.priceTime = LocalDateTime.now()
        }
        val crypto1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPriceHistory(mutableListOf(priceHistory1)).build()

        val priceHistory2 = PriceHistory(CryptoSymbol.BTCUSDT, 2.0).apply {
            this.price = 200.0
            this.priceTime = LocalDateTime.now()
        }
        val crypto2 = CryptocurrencyBuilder().withName(CryptoSymbol.ETHUSDT).withPriceHistory(mutableListOf(priceHistory2)).build()

        val exchangeSystem = aExchangeSystem().withCryptocurrencies(mutableSetOf(crypto1, crypto2)).build()

        val prices = exchangeSystem.getPrices()

        assertEquals(2, prices.size)
    }

    @Test
    fun `getPrices returns empty list when there are no cryptocurrencies`() {
        val crypto1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
        val exchangeSystem = aExchangeSystem().withCryptocurrencies(mutableSetOf(crypto1)).build()

        val prices = exchangeSystem.getPrices()

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
    fun `ordersByUser returns empty list when user has no actives orders`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)

        val userOrders = exchangeSystem.active0rdersByUser(user)

        assertTrue(userOrders.isEmpty())
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


    @Test
    fun `should throw exception when user is not registered`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        val cryptocurrency = aCrypto()
        val amount = 10.0
        val price = 50000.0
        val type = IntentionType.BUY

        assertThrows<IllegalArgumentException> {
            exchangeSystem.publishOrder(user, cryptocurrency, amount, price, type)
        }
    }

    @Test
    fun `should throw exception when the buyer is the same as the seller`() {
        val exchangeSystem = aExchangeSystem().build()
        val user = aUser()
        exchangeSystem.registerUser(user)
        val cryptocurrency = aCrypto()
        val amount = 10.0
        val price = 50000.0
        val order = exchangeSystem.publishOrder(user, cryptocurrency, amount, price, IntentionType.BUY)

        assertThrows<java.lang.Exception> {
            exchangeSystem.startTransaction(order, user)
        }
    }

    @Test
    fun `should cancel transaction when seller is the same as transaction seller`() {
        val exchangeSystem = aExchangeSystem().build()
        val seller = aUser()
        val buyer = aUser2()
        exchangeSystem.registerUser(seller)
        exchangeSystem.registerUser(buyer)

        val order = exchangeSystem.publishOrder(seller, aCrypto(), 10.0, 50000.0, IntentionType.SELL)
        val transaction = exchangeSystem.startTransaction(order, buyer)

        exchangeSystem.sellerCancelsTransaction(transaction)

        assertEquals(TransactionStatus.CANCELLED_BY_USER, transaction.status)
        assertEquals(-20, seller.score)
    }

    @Test
    fun `should mark transaction as paid when buyer pays`() {
        val exchangeSystem = aExchangeSystem().build()
        val buyer = aUser()
        val seller = aUser2()
        exchangeSystem.registerUser(buyer)
        exchangeSystem.registerUser(seller)
        val order = exchangeSystem.publishOrder(seller, aCrypto(), 10.0, 50000.0, IntentionType.SELL)
        val transaction = exchangeSystem.startTransaction(order, buyer)

        exchangeSystem.buyerPaidTransaction(transaction)

        assertEquals(TransactionStatus.PAID, transaction.status)
    }

    @Test
    fun `should decrease buyer score when buyer cancels transaction`() {
        val exchangeSystem = aExchangeSystem().build()
        val buyer = aUser()
        val seller = aUser2()
        exchangeSystem.registerUser(buyer)
        exchangeSystem.registerUser(seller)
        val order = exchangeSystem.publishOrder(seller, aCrypto(), 10.0, 50000.0, IntentionType.SELL)
        val transaction = exchangeSystem.startTransaction(order, buyer)

        exchangeSystem.buyerCancelTransaction(transaction)

        assertEquals(TransactionStatus.CANCELLED_BY_USER, transaction.status)
        assertEquals(-20, buyer.score)
    }

    @Test
    fun `should mark transaction as confirmed when seller confirms`() {
        val exchangeSystem = aExchangeSystem().build()
        val buyer = aUser()
        val seller = aUser2()
        exchangeSystem.registerUser(buyer)
        exchangeSystem.registerUser(seller)
        val order = exchangeSystem.publishOrder(seller, aCrypto(), 10.0, 50000.0, IntentionType.SELL)
        val transaction = exchangeSystem.startTransaction(order, buyer)
        transaction.paid()

        exchangeSystem.sellerConfirmTransaction(transaction)

        assertEquals(TransactionStatus.CONFIRMED, transaction.status)
    }

}