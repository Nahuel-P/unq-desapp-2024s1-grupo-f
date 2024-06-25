package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.PriceHistoryBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PriceHistoryMapperTest {
    @Test
    fun `toDTO converts list of PriceHistory to PriceHistoryResponseDTO`() {
        val priceHistoryList = listOf(
            PriceHistoryBuilder().withCryptocurrency(CryptoSymbol.BTCUSDT).withPrice(50000.0).withPriceTime(LocalDateTime.now()).build(),
            PriceHistoryBuilder().withCryptocurrency(CryptoSymbol.BTCUSDT).withPrice(51000.0).withPriceTime(LocalDateTime.now().plusHours(1)).build()
        )

        val dto = PriceHistoryMapper.toDTO(priceHistoryList)

        assertEquals(CryptoSymbol.BTCUSDT, dto.symbol)
        assertEquals(2, dto.history.size)
        assertEquals(50000.0, dto.history[0].price)
        assertEquals(51000.0, dto.history[1].price)
    }

    @Test
    fun `toDTO returns correct symbol for PriceHistoryResponseDTO`() {
        val priceHistoryList = listOf(
            PriceHistoryBuilder().withCryptocurrency(CryptoSymbol.ETHUSDT).withPrice(4000.0).withPriceTime(LocalDateTime.now().plusHours(1)).build()
        )

        val dto = PriceHistoryMapper.toDTO(priceHistoryList)

        assertEquals(CryptoSymbol.ETHUSDT, dto.symbol)
    }
    
}