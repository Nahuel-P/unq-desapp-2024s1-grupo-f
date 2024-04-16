package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override fun registerUser(userCreateRequest: UserCreateRequestDTO): UserResponseDTO? {
        require(!(userCreateRequest.email == null || userCreateRequest.email!!.isEmpty())) { "Email must not be null or empty" }
        val user = User()
        user.email = userCreateRequest.email
        return user.firstName?.let { user.email?.let { it1 -> UserResponseDTO(it, it1) } }
    }
}