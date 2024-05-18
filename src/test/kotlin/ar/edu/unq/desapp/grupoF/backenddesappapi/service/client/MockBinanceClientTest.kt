package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("integration")
class MockBinanceClientTest(@Autowired val binanceClientService: IBinanceClientService) {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val symbol = CryptoSymbol.BTCUSDT

        val result = binanceClientService.getCryptoCurrencyPrice(symbol)

        val lowerBound = 50000.0
        val upperBound = 70000.0

        assertTrue(result.price!! in lowerBound..upperBound)
    }

}