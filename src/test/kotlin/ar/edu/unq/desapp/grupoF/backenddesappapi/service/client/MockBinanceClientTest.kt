package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "integration")
class MockBinanceClientTest {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val binanceClientService = MockBinanceClientService()
        val symbol = CryptoSymbol.BTCUSDT

        val result = binanceClientService.getCryptoCurrencyPrice(symbol)

        val lowerBound = 50000.0
        val upperBound = 70000.0

        assertTrue(result.price!! in lowerBound..upperBound)
    }

}