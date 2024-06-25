package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints for user")
@Validated
@Transactional
class UserController {

    @Autowired
    private lateinit var commonService: ICommonService

    @Autowired
    private lateinit var userService: IUserService

    @Operation(summary = "Get all registered users", responses = [
        ApiResponse(responseCode = "200", description = "List of all users", content = [
            Content(mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = UserResponseDTO::class))
            )
        ]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])
    ])
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<Any> {
        return try {
            val users = userService.getUsers()
            val usersResponse = users.map { user -> UserMapper.toDTO(user) }
            ResponseEntity.status(HttpStatus.OK).body(usersResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

    @Operation(summary = "Get user by ID", responses = [
        ApiResponse(responseCode = "200", description = "User details", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = UserResponseDTO::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])

    @GetMapping("{id}")
    fun getUserByID(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            val user = commonService.getUser(id)
            val userResponse = UserMapper.toDTO(user)
            ResponseEntity.status(HttpStatus.OK).body(userResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message));
        }
    }

    @Operation(summary = "Get operated volume by user ID within a date range", responses = [
        ApiResponse(responseCode = "200", description = "Operated volume details", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = UserVolumeReport::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
    @GetMapping("/operatedVolume/{userId}/{startDate}/{endDate}")
    fun getOperatedVolume(
        @PathVariable userId: Long,
        @PathVariable startDate: String,
        @PathVariable endDate: String
    ): ResponseEntity<Any> {
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