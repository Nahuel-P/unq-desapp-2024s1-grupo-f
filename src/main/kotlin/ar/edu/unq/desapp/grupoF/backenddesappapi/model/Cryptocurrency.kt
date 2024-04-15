package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import java.time.LocalDateTime

class Cryptocurrency {


    var name: CryptoSymbol? = null
    var createdAt: LocalDateTime? = null
    var priceHistory: MutableList<PriceHistory> = mutableListOf()

    fun lastPrice(): PriceHistory? {
        return priceHistory.maxByOrNull { it.priceTime }
    }

    fun pricesOver24hs(): List<PriceHistory> {
        return priceHistory.filter { it.priceTime.isAfter(LocalDateTime.now().minusDays(1)) }
    }

}

