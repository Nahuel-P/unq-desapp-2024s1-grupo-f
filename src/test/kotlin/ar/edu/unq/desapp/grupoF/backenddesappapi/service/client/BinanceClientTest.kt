package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

// BinanceClientTest.kt
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "dev")
class BinanceClientTest {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val binanceClient = BinanceClient()
        val symbol = CryptoSymbol.BTCUSDT

        val result = binanceClient.getCryptoCurrencyPrice(symbol)

        val lowerBound = 70000.0
        val upperBound = 90000.0

        assertTrue(result.price!! in lowerBound..upperBound)
    }

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided2`() {
        val dolarApiClient = DolarApiClient()
        val result = dolarApiClient.getRateUsdToArs().compra

        val lowerBound = 900.0
        val upperBound = 1200.0

        assertTrue(result!! in lowerBound..upperBound)
    }

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided22`() {
        val binanceClient = BinanceClient()
        val symbols = mutableListOf(CryptoSymbol.BTCUSDT, CryptoSymbol.ETHUSDT)

        val results = binanceClient.getAllCryptoCurrencyPrices(symbols)

        val lowerBoundBTC = 70000.0
        val upperBoundBTC = 90000.0

        val lowerBoundETH = 2000.0
        val upperBoundETH = 4000.0

        assertTrue(results.find { it.symbol == CryptoSymbol.BTCUSDT.toString() }?.price!! in (lowerBoundBTC..upperBoundBTC))
        assertTrue(results.find { it.symbol == CryptoSymbol.ETHUSDT.toString() }?.price!! in lowerBoundETH..upperBoundETH)
    }
}