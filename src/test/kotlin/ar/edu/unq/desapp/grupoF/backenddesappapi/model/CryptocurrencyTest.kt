import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class CryptocurrencyTest {

    fun aCryptocurrency(): CryptocurrencyBuilder {
        return CryptocurrencyBuilder()
            .withName(CryptoSymbol.BTCUSDT)
            .withCreated(LocalDateTime.now())
    }

    @Test
    fun `should create a cryptocurrency when it has valid data`() {
        assertDoesNotThrow { aCryptocurrency().build() }
    }

    @Test
    fun `throw exception for null name`() {
        assertThrows<IllegalArgumentException> {
            aCryptocurrency().withName(null).build()
        }
    }

    @Test
    fun `lastPrice returns the most recent price history`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPrice = PriceHistory(cryptocurrency, 100.0).apply {
            this.priceTime = LocalDateTime.now().minusDays(1)
        }
        val recentPrice = PriceHistory(cryptocurrency, 200.0).apply {
            this.priceTime = LocalDateTime.now()
        }

        cryptocurrency.priceHistory = mutableListOf(oldPrice, recentPrice)

        assertFalse(cryptocurrency.priceHistory.isEmpty())
        assertEquals(recentPrice, cryptocurrency.lastPrice())
    }

    @Test
    fun `pricesOver24hs returns only the prices from the last 24 hours`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPrice = PriceHistory(cryptocurrency, 100.0).apply {
            this.priceTime = LocalDateTime.now().minusDays(2)
        }
        val recentPrice = PriceHistory(cryptocurrency, 200.0).apply {
            this.priceTime = LocalDateTime.now()
        }

        cryptocurrency.priceHistory = mutableListOf(oldPrice, recentPrice)

        val pricesOver24hs = cryptocurrency.pricesOver24hs()

        assertTrue(pricesOver24hs.contains(recentPrice))
        assertFalse(pricesOver24hs.contains(oldPrice))
    }

    @Test
    fun `pricesOver24hs returns an empty list when there are no prices from the last 24 hours`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPrice = PriceHistory(cryptocurrency, 100.0).apply {
            this.priceTime = LocalDateTime.now().minusDays(2)
        }

        cryptocurrency.priceHistory = mutableListOf(oldPrice)

        val pricesOver24hs = cryptocurrency.pricesOver24hs()

        assertTrue(pricesOver24hs.isEmpty())
    }
}