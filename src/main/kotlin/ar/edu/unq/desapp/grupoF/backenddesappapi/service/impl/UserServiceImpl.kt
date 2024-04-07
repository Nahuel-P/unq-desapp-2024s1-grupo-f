package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.IUserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : IUserService {

    @Autowired
    private lateinit var userRepository: IUserRepository

    override fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        address: String,
        password: String,
        initialCvu: String,
        initialWallet: String
    ): UserExchange {
        val user = UserExchange(firstName, lastName, email, address, password, initialCvu, initialWallet)
        return userRepository.save(user)
    }

    fun getUser(userId: Long): UserExchange {
        return userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found with id: $userId") }
    }
}