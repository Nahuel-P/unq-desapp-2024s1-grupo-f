package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints for user")
@Validated
@Transactional
class UserController(private val userService: IUserService) {
    @Operation(summary = "Register a new user")
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

    @Operation(summary = "Get all registered users")
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any> {
        val users = userService.getUsers()
        return ResponseEntity.status(HttpStatus.OK).body(users)
    }

    @Operation(summary = "Get all data from an user by id")
    @GetMapping("{id}")
    fun getUserByID(@PathVariable id: Long): ResponseEntity<Any> {
        val user = userService.findUser(id)
        return ResponseEntity.status(HttpStatus.OK).body(user)
    }

}