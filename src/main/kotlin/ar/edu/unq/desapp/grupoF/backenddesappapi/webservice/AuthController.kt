package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IAuthService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.LoginRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "Register a new user")
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

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginRequestDTO): ResponseEntity<Any> {
        return try {
            val tokenAuth = authService.login(loginDTO)
            ResponseEntity.status(HttpStatus.OK).body(mapOf("token_auth" to tokenAuth))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}