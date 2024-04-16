package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override fun registerUser(userCreateRequest: UserCreateRequestDTO): User {
        require(!(userCreateRequest.email == null || userCreateRequest.email!!.isEmpty())) { "Email must not be null or empty" }
        val user = User()
        user.email = userCreateRequest.email
        return user
    }
}