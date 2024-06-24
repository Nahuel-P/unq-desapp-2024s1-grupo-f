package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.*
import java.time.LocalDateTime

data class PriceHistoryResponseDTO (
    val symbol: CryptoSymbol?,
    val history : List<HistoryResponseDTO>
)

data class HistoryResponseDTO (
    val price: Double?,
    var priceTime: String?
)