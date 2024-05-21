package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

data class UserResponseDTO(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String
)