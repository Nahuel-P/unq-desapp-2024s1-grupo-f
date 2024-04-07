package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

class PriceHistory(
    var price: Double = 0.0,
    var dateTime: LocalDateTime? = null
)