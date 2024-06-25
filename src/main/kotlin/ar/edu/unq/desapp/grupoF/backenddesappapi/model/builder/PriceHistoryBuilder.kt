package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import java.time.LocalDateTime

class PriceHistoryBuilder {
    private var crypto: CryptoSymbol? = null
    private var price: Double = 0.00
    private var priceTime: LocalDateTime = LocalDateTime.now()

    fun build(): PriceHistory {
        val priceHistory = PriceHistory()
        priceHistory.cryptocurrency = this.crypto!!
        priceHistory.price = this.price
        priceHistory.priceTime = this.priceTime
        return priceHistory
    }

    fun withCryptocurrency(symbol: CryptoSymbol?): PriceHistoryBuilder {
        this.crypto = symbol
        return this
    }

    fun withPrice(price: Double): PriceHistoryBuilder {
        this.price = price
        return this
    }

    fun withPriceTime(priceTime: LocalDateTime): PriceHistoryBuilder {
        this.priceTime = priceTime
        return this
    }
}