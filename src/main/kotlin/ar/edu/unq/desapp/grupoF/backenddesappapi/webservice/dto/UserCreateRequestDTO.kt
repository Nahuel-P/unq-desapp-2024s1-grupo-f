package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class UserCreateRequestDTO(
    @field:NotBlank(message = "First name cannot be blank")
    var firstName: String? = null,

    @field:NotBlank(message = "Last name cannot be blank")
    var lastName: String? = null,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email should be valid")
    var email: String? = null,

    @field:NotBlank(message = "Password cannot be blank")
    var password: String? = null
)