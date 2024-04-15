package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.*

class ExchangeSystemBuilder {
    private var cryptocurrencies: MutableSet<Cryptocurrency> = mutableSetOf()

    fun build(): ExchangeSystem {
        val exchangeSystem = ExchangeSystem()
        exchangeSystem.cryptocurrencies = this.cryptocurrencies
        return exchangeSystem
    }

    fun withCryptocurrencies(cryptocurrencies: MutableSet<Cryptocurrency>): ExchangeSystemBuilder {
        this.cryptocurrencies = cryptocurrencies
        return this
    }

}