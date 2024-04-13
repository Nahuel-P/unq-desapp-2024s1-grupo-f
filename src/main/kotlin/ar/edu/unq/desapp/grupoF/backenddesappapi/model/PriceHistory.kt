package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

class PriceHistory(
    var id: Long? = null,
    var cryptocurrency: Cryptocurrency? = null,
    var price: Double? = 0.0,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)