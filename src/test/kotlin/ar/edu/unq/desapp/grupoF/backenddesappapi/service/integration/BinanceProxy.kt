package ar.edu.unq.desapp.grupoF.backenddesappapi.service.integration
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BinanceProxy {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val binanceProxyService = BinanceProxyService()
        val symbol = CryptoSymbol.BTCUSDT


        val result = binanceProxyService.getCryptoCurrencyPrice(symbol)

        assertEquals("", result)
    }
}