package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints for user")
@Validated
@Transactional
class UserController{

    @Autowired
    private lateinit var commonService: ICommonService
    @Autowired
    private lateinit var userService: IUserService
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userCreateRequest: UserCreateDTO): ResponseEntity<Any> {
        return try {
            val user = userService.registerUser(userCreateRequest)
            val userResponse = UserMapper.userToDTO(user)
            ResponseEntity.status(HttpStatus.OK).body(userResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @Operation(summary = "Get all registered users")
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any> {
        return try {
            val users = userService.getUsers()
            val usersResponse = users.map { user -> UserMapper.userToDTO(user) }
            ResponseEntity.status(HttpStatus.OK).body(usersResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

    @GetMapping("{id}")
    fun getUserByID(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            val user = commonService.getUser(id)
            val userResponse = UserMapper.userToDTO(user)
            ResponseEntity.status(HttpStatus.OK).body(userResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

    // En UserController.kt

    @GetMapping("/operatedVolume/{userId}/{startDate}/{endDate}")
    fun getOperatedVolume(@PathVariable userId: Long, @PathVariable startDate: String, @PathVariable endDate: String): ResponseEntity<Any> {
        return try {
            val startDateTime = LocalDate.parse(startDate).atStartOfDay()
            val endDateTime = LocalDate.parse(endDate).atTime(23, 59, 59)
            val report = userService.getOperatedVolumeBy(userId, startDateTime, endDateTime)
            ResponseEntity.status(HttpStatus.OK).body(report)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

}