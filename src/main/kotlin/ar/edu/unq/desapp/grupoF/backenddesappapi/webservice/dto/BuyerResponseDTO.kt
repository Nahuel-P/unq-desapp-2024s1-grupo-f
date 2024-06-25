package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

data class BuyerResponseDTO (
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var wallet: String
)