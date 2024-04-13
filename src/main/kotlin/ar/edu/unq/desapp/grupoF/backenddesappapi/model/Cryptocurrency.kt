package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

data class Cryptocurrency(
    var id: String? = null,
    var name: String? = null,
    var createdAt: LocalDateTime? = null,
    var priceHistory: MutableList<PriceHistory> = mutableListOf()
)