package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
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

    @PostMapping("/registerUser")
    fun registerUser(@Valid @RequestBody userCreateRequest: UserCreateRequestDTO): ResponseEntity<Any> {
        return try {
            userService.registerUser(userCreateRequest)
            val response = mapOf(
                "message" to "User registration successful",
            )
            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(mapOf("message" to e.message), HttpStatus.BAD_REQUEST)
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): Map<String, String> {
        return mapOf("message" to (e.bindingResult.allErrors[0].defaultMessage ?: "Validation error"))
    }
}