package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

class PriceHistory {

    var cryptocurrency: Cryptocurrency? = null
    var price: Double? = 0.0
    var priceTime: LocalDateTime = LocalDateTime.now()
}