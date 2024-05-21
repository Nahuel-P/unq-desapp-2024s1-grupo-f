package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "integration")
class MockBinanceClientServiceTest {

    @Test
    fun `getCryptoCurrencyPrice returns correct price for given symbol`() {
        val service = MockBinanceClientService()
        val result = service.getCryptoCurrencyPrice(CryptoSymbol.BTCUSDT)
        assertEquals("BTCUSDT", result.symbol)
        assertEquals(50000.0, result.price)
    }

    @Test
    fun `getAllCryptoCurrencyPrices returns correct prices for given symbols`() {
        val service = MockBinanceClientService()
        val symbols = mutableListOf(CryptoSymbol.BTCUSDT, CryptoSymbol.ETHUSDT)
        val results = service.getAllCryptoCurrencyPrices(symbols)

        val btcPrice = results.find { it.symbol == "BTCUSDT" }
        val ethPrice = results.find { it.symbol == "ETHUSDT" }

        assertEquals("BTCUSDT", btcPrice?.symbol)
        assertEquals(50000.0, btcPrice?.price)

        assertEquals("ETHUSDT", ethPrice?.symbol)
        assertEquals(4000.0, ethPrice?.price)
    }
}