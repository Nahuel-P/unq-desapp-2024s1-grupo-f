package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CryptocurrencyMapperTest {
    @Test
    fun `toDTO converts single Cryptocurrency to CryptocurrencyPriceDTO`() {
        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPrice(50000.0).build()
        val dto = CryptocurrencyMapper.toDTO(cryptocurrency)

        assertEquals(CryptoSymbol.BTCUSDT, dto.symbol)
        assertEquals(50000.0, dto.price)
    }

    @Test
    fun `toDTO converts list of Cryptocurrencies to list of CryptocurrencyPriceDTOs`() {
        val cryptocurrencies = listOf(
            CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPrice(50000.0).build(),
            CryptocurrencyBuilder().withName(CryptoSymbol.ETHUSDT).withPrice(4000.0).build()
        )

        val dtos = CryptocurrencyMapper.toDTO(cryptocurrencies)

        assertEquals(2, dtos.size)
        assertEquals(CryptoSymbol.BTCUSDT, dtos[0].symbol)
        assertEquals(50000.0, dtos[0].price)
        assertEquals(CryptoSymbol.ETHUSDT, dtos[1].symbol)
        assertEquals(4000.0, dtos[1].price)
    }
}