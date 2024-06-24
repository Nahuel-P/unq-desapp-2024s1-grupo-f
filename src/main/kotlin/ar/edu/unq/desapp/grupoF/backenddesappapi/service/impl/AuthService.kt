package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService : IAuthService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    override fun registerUser(user: User): User {
        if (userRepository.existsByEmail(user.email!!)) {
            throw Exception("User with email ${user.email} already exists")
        }
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }
}
