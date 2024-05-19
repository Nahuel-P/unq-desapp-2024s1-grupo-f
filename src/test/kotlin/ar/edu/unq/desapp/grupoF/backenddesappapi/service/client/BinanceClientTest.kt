package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
class BinanceClientTest {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val binanceClient = BinanceClient()
        val symbol = CryptoSymbol.BTCUSDT

        val result = binanceClient.getCryptoCurrencyPrice(symbol)

        val lowerBound = 50000.0
        val upperBound = 70000.0

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

        val lowerBoundBTC = 50000.0
        val upperBoundBTC = 70000.0

        val lowerBoundETH = 2000.0
        val upperBoundETH = 4000.0

        assertTrue(results.find { it.symbol == CryptoSymbol.BTCUSDT.toString() }?.price!! in (lowerBoundBTC..upperBoundBTC))
        assertTrue(results.find { it.symbol == CryptoSymbol.ETHUSDT.toString() }?.price!! in lowerBoundETH..upperBoundETH)
    }
}