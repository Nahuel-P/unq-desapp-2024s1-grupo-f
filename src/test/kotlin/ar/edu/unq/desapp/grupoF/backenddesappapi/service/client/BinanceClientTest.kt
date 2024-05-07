package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BinanceClientTest {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val binanceClient = BinanceClient()
        val symbol = CryptoSymbol.BTCUSDT


        val result = binanceClient.getCryptoCurrencyPrice(symbol)

        assertEquals("", result)
    }

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided2`() {
        val dolarApiClient = DolarApiClient()
        val result = dolarApiClient.getRateUsdToArs()
        assertEquals("", result)
    }

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided22`() {
        val binanceClient = BinanceClient()
        val symbols = mutableListOf(CryptoSymbol.BTCUSDT, CryptoSymbol.ETHUSDT)
        val result = binanceClient.getAllCryptoCurrencyPrices(symbols)
        assertEquals("", result)
    }
}