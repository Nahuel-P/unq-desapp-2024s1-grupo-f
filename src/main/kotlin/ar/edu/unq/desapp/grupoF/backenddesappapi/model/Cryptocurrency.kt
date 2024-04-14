package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import java.time.LocalDateTime

data class Cryptocurrency(
    var id: String? = null,
    var name: CryptoSymbol? = null,
    var createdAt: LocalDateTime? = null,
    var priceHistory: MutableList<PriceHistory> = mutableListOf()
)