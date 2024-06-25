package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IAuthService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.LoginRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl : IAuthService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var tokenService: TokenServiceImpl

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    override fun registerUser(userDto: UserCreateDTO): User {
        val user = UserMapper.toModel(userDto)
        if (userRepository.existsByEmail(user.email!!)) {
            throw Exception("User with email ${user.email} already exists")
        }
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    override fun login(loginDTO: LoginRequestDTO): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDTO.email,
                loginDTO.password
            )
        )
        val user: User? = userRepository.findByEmail(loginDTO.email)
        return tokenService.generateJWT(user!!)
    }
}
