package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.springframework.stereotype.Service


@Service
class UserServiceImpl( private val userRepository: UserRepository) : UserService{
//    private val objectMapper = jacksonObjectMapper()

    override fun registerUser(user: User): User {
        if (userRepository.existsByEmail(user.email!!)) {
            throw Exception("User with email ${user.email} already exists")
        }
        return userRepository.save(user)
    }
}