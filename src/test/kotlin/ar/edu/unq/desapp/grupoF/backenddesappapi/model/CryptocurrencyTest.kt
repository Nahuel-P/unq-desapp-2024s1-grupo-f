
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
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
    fun `lastPrice returns current price`() {
        val cryptocurrency = aCryptocurrency().build()
        cryptocurrency.price = 100.0
        assertEquals(100.0, cryptocurrency.lastPrice())
    }
    @Test
    fun `getLast24hsQuotes returns only last 24 hours quotes`() {
        val cryptocurrency = aCryptocurrency().build()
        val oldPrice = PriceHistory().apply {
            this.cryptocurrency = cryptocurrency
            this.price = 100.0
            this.priceTime = LocalDateTime.now().minusDays(2)
        }
        val recentPrice = PriceHistory().apply {
            this.cryptocurrency = cryptocurrency
            this.price = 200.0
            this.priceTime = LocalDateTime.now()
        }
        cryptocurrency.priceHistory = mutableListOf(oldPrice, recentPrice)

        val last24hsQuotes = cryptocurrency.getLast24hsQuotes()

        assertTrue(last24hsQuotes.contains(recentPrice))
        assertFalse(last24hsQuotes.contains(oldPrice))
    }

}