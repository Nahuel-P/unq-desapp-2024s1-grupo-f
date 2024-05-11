package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints for user")
@Validated
@Transactional
class UserController(private val userService: UserService) {
    @Operation(summary = "Registrar un usuario")
    @PostMapping("/registerUser")
    fun registerUser(@Valid @RequestBody userCreateRequest: UserCreateRequestDTO): ResponseEntity<Any> {
        try {
            val anUser = UserBuilder()
                .withFirstName(userCreateRequest.firstName!!)
                .withLastName(userCreateRequest.lastName!!)
                .withEmail(userCreateRequest.email!!)
                .withAddress(userCreateRequest.address!!)
                .withPassword(userCreateRequest.password!!)
                .withCvu(userCreateRequest.cvu!!)
                .withWalletAddress(userCreateRequest.walletAddress!!)
                .build()

            val newUser = userService.registerUser(anUser)
            return ResponseEntity.status(HttpStatus.OK).body(mapOf("message" to "User registration successful. Id: ${newUser.id}"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    fun handleValidationExceptions(e: MethodArgumentNotValidException): Map<String, String> {
//        return mapOf("message" to (e.bindingResult.allErrors[0].defaultMessage ?: "Validation error"))
//    }
}