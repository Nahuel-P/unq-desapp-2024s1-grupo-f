package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType

data class OrderResponseDTO(
    var userId: Long,
    var cryptocurrency: CryptoSymbol,
    var amount: Double,
    var price: Double,
    var type: IntentionType,
    var arsPrice : Double
)