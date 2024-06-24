import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.PriceHistoryBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.*
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
        assertDoesNotThrow { aCryptocurrency().build() }
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
        val cryptocurrency = aCryptocurrency().build()
        cryptocurrency.price = 100.0
        assertEquals(100.0, cryptocurrency.lastPrice())
    }

    @Test
    fun `should return last price when called lastPrice`() {
        val cryptocurrency = aCryptocurrency().withPrice(100.0).build()
        assertEquals(100.0, cryptocurrency.lastPrice())
    }

    @Test
    fun `should return only last 24 hours quotes when called getLast24hsQuotes`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPrice = PriceHistoryBuilder().withCryptocurrency(cryptocurrency).withPrice(100.0).withPriceTime(LocalDateTime.now().minusDays(2)).build()
        val recentPrice = PriceHistoryBuilder().withCryptocurrency(cryptocurrency).withPrice(200.0).withPriceTime(LocalDateTime.now()).build()
        val futurePrice = PriceHistoryBuilder().withCryptocurrency(cryptocurrency).withPrice(200.0).withPriceTime(LocalDateTime.now().plusDays(2)).build()
        cryptocurrency.priceHistory = mutableListOf(oldPrice, recentPrice, futurePrice)
        val last24hsQuotes = cryptocurrency.getLast24hsQuotes()
        assertFalse(last24hsQuotes.contains(oldPrice))
        assertTrue(last24hsQuotes.contains(recentPrice))
        assertFalse(last24hsQuotes.contains(futurePrice))
    }

    @Test
    fun `should return empty list when no quotes in last 24 hours`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPrice = PriceHistoryBuilder().withCryptocurrency(cryptocurrency).withPrice(100.0).withPriceTime(LocalDateTime.now().minusDays(2)).build()
        cryptocurrency.priceHistory = mutableListOf(oldPrice)
        val last24hsQuotes = cryptocurrency.getLast24hsQuotes()
        assertTrue(last24hsQuotes.isEmpty())
    }



}