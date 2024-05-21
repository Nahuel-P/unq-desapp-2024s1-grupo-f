package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints for user")
@Validated
@Transactional
class UserController{

    @Autowired
    private lateinit var userService: IUserService
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userCreateRequest: UserCreateDTO): ResponseEntity<Any> {
        try {
            val user = userService.registerUser(userCreateRequest)
            var userResponse = UserMapper.userToDTO(user)
            return ResponseEntity.status(HttpStatus.OK).body(userResponse)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

    @Operation(summary = "Get all registered users")
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any> {
        try {
            val users = userService.getUsers()
            var usersResponse = users.map { user -> UserMapper.userToDTO(user) }
            return ResponseEntity.status(HttpStatus.OK).body(usersResponse)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

    @Operation(summary = "Get all data from an user by id")
    @GetMapping("{id}")
    fun getUserByID(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            val user = userService.getUser(id)
            var userResponse = UserMapper.userToDTO(user)
            return ResponseEntity.status(HttpStatus.OK).body(userResponse)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

}