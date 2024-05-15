package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

data class OrderRequestDTO(
    var userId: Long,
    var cryptocurrency: String,
    var amount: Double,
    var price: Double,
    var type: String
)