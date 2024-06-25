package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.PriceHistoryBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class CryptocurrencyTest {

    private fun aCryptocurrency(): CryptocurrencyBuilder {
        return CryptocurrencyBuilder()
            .withName(CryptoSymbol.BTCUSDT)
    }

    @Test
    fun `should create a cryptocurrency when it has valid data`() {
        assertDoesNotThrow { aCryptocurrency().withName(CryptoSymbol.DOTUSDT).build() }
    }

    @Test
    fun `throw exception for null name`() {
        assertThrows<IllegalArgumentException> {
            aCryptocurrency().withName(null).build()
        }
    }

    @Test
    fun `throw exception for negative price`() {
        assertThrows<IllegalArgumentException> {
            aCryptocurrency().withPrice(-1.0).build()
        }
    }

    @Test
    fun `lastPrice returns current price`() {
        val cryptocurrency = aCryptocurrency().withPrice(100.0).build()
        assertEquals(100.0, cryptocurrency.lastPrice())
    }

    @Test
    fun `should return last price when called lastPrice`() {
        val cryptocurrency = aCryptocurrency().withPrice(100.0).build()
        assertEquals(100.0, cryptocurrency.lastPrice())
    }

    @Test
    fun `getLast24hsQuotes returns only quotes from the last 24 hours`() {
        val cryptocurrency = aCryptocurrency().build()

        val oldPriceHistory = PriceHistoryBuilder().withCryptocurrency(cryptocurrency.name).withPrice(100.0)
            .withPriceTime(LocalDateTime.now().minusDays(2)).build()
        val recentPriceHistory = PriceHistoryBuilder().withCryptocurrency(cryptocurrency.name).withPrice(200.0)
            .withPriceTime(LocalDateTime.now()).build()
        val futurePriceHistory = PriceHistoryBuilder().withCryptocurrency(cryptocurrency.name).withPrice(300.0)
            .withPriceTime(LocalDateTime.now().plusDays(2)).build()

        val priceHistories = listOf(oldPriceHistory, recentPriceHistory, futurePriceHistory)

        val result = cryptocurrency.getLast24hsQuotes(priceHistories)

        assertEquals(listOf(recentPriceHistory), result)
    }

    @Test
    fun `getLast24hsQuotes returns empty list when no quotes in last 24 hours`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPriceHistory = PriceHistoryBuilder().withPrice(100.0).withPriceTime(LocalDateTime.now().minusDays(2))
            .withCryptocurrency(cryptocurrency.name).build()
        val priceHistories = listOf(oldPriceHistory)

        val result = cryptocurrency.getLast24hsQuotes(priceHistories)

        assertEquals(emptyList<PriceHistory>(), result)
    }

}