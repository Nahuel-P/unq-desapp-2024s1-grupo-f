package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    private val objectMapper = jacksonObjectMapper()

    override fun registerUser(userCreateRequest: UserCreateRequestDTO): String {
        if (userCreateRequest.email == null || userCreateRequest.email!!.isEmpty()) {
            return objectMapper.writeValueAsString(mapOf("message" to "Email must not be null or empty"))
        }
        val user = User()
        user.email = userCreateRequest.email
        return user.firstName?.let { user.email?.let { it1 -> objectMapper.writeValueAsString(UserResponseDTO(it, it1)) } }
            ?: objectMapper.writeValueAsString(mapOf("message" to "User registration failed"))
    }
}