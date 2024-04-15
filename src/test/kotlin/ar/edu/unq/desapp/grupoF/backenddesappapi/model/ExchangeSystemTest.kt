package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.ExchangeSystemBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ExchangeSystemTest {

    fun aCrypto(): Cryptocurrency {
        return CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
    }

    fun aCryptocurrencySet(): MutableSet<Cryptocurrency> {
        return mutableSetOf(aCrypto())
    }

    fun aExchangeSystem(): ExchangeSystemBuilder {
        return ExchangeSystemBuilder().
                withCryptocurrencies(aCryptocurrencySet())

    }

    @Test
    fun `should create a exchange system when it has valid data`() {
        assertDoesNotThrow { aExchangeSystem().build() }
    }


}