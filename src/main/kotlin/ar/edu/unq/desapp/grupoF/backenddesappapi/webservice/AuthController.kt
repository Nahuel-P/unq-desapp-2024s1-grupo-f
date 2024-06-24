//package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice
//
//import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
//import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
//import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
//import io.swagger.v3.oas.annotations.Operation
//import jakarta.validation.Valid
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//
//class AuthController {
//
//    lateinit var authService : AuthService
//
//    @Operation(summary = "Register a new user")
//    @PostMapping("/register")
//    fun register(@Valid @RequestBody userCreateRequest: UserCreateDTO): ResponseEntity<Any> {
//        return try {
//            val user = userService.registerUser(userCreateRequest)
//            val userResponse = UserMapper.userToDTO(user)
//            ResponseEntity.status(HttpStatus.OK).body(userResponse)
//        } catch (e: Exception) {
//            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
//        }
//    }
//}