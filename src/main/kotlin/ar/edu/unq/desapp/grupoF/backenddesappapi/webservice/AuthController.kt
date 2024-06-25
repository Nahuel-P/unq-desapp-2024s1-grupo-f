package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IAuthService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.LoginRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    lateinit var authService: IAuthService
    @Operation(summary = "Register a new user", responses = [
        ApiResponse(responseCode = "200", description = "User registered successfully", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = UserResponseDTO::class)
        )]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userCreateRequest: UserCreateDTO): ResponseEntity<Any> {
        return try {
            val newUser = authService.registerUser(userCreateRequest)
            val userResponse = UserMapper.toDTO(newUser)
            ResponseEntity.status(HttpStatus.OK).body(userResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @Operation(summary = "Login an user", responses = [
        ApiResponse(responseCode = "200", description = "Login an user an provides token auth", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = String::class)
        )]),
        ApiResponse(responseCode = "400", description = "Bad Credentials", content = [Content()])])
    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginRequestDTO): ResponseEntity<Any> {
        return try {
            val tokenAuth = authService.login(loginDTO)
            ResponseEntity.status(HttpStatus.OK).body(tokenAuth)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}