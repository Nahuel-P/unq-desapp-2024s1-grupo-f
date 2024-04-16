package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import java.time.LocalDateTime

class PriceHistory(symbol: CryptoSymbol, price: Double) {

    var cryptocurrency: CryptoSymbol? = symbol
    var price: Double? = price
    var priceTime: LocalDateTime = LocalDateTime.now()
}