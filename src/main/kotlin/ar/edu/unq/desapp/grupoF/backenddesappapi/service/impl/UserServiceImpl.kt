package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import org.springframework.stereotype.Service


@Service
class UserServiceImpl( private val userRepository: UserRepository) : IUserService{
//    private val objectMapper = jacksonObjectMapper()

    override fun registerUser(user: User): User {
        if (userRepository.existsByEmail(user.email!!)) {
            throw Exception("User with email ${user.email} already exists")
        }
        return userRepository.save(user)
    }

    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun findUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { Exception("User with id $id not found") }
    }
}