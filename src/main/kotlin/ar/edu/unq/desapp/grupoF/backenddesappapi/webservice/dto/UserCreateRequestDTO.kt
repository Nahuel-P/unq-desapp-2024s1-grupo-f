package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class UserCreateRequestDTO(
    @field:NotBlank(message = "First name cannot be blank")
    @field:Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    var firstName: String? = null,

    @field:NotBlank(message = "Last name cannot be blank")
    @field:Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    var lastName: String? = null,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email should be valid")
    var email: String? = null,

    @field:NotBlank(message = "Address cannot be blank")
    @field:Size(min = 10, max = 30, message = "Address must be between 10 and 30 characters")
    var address: String? = null,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,}$", message = "Password must contain at least 1 lowercase letter, 1 uppercase letter, 1 special character and be at least 6 characters long")
    var password: String? = null,

    @field:NotBlank(message = "CVU cannot be blank")
    @field:Size(min = 22, max = 22, message = "CVU must be 22 characters")
    var cvu: String? = null,

    @field:NotBlank(message = "Wallet address cannot be blank")
    @field:Size(min = 8, max = 8, message = "Wallet address must be 8 characters")
    var walletAddress: String? = null
)