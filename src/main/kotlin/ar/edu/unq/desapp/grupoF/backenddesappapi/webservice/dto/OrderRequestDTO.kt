package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType

data class OrderRequestDTO(
    var userId: Long,
    var cryptocurrency: String,
    var amount: Double,
    var price: Double,
    var type: IntentionType
)