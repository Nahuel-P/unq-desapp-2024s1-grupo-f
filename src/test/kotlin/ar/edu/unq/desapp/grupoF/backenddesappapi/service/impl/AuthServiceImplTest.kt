package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.LoginRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class AuthServiceImplTest {

    @InjectMocks
    lateinit var authService: AuthServiceImpl

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @Mock
    lateinit var tokenService: TokenServiceImpl

    @Mock
    lateinit var authenticationManager: AuthenticationManager

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `registerUser should create a new user when email is not already registered`() {
        val userDto = UserCreateDTO(
            "michael",
            "scott",
            "prisonmike@test.com",
            "1725 Slough Avenue, Scranton",
            "Password!1",
            "1234567890123456789012",
            "12345678"
        )
        val user = UserMapper.toModel(userDto)
        user.password = passwordEncoder.encode(user.password)

        Mockito.`when`(userRepository.existsByEmail(user.email!!)).thenReturn(false)
        Mockito.`when`(userRepository.save(any(User::class.java))).thenReturn(user)

        val result = authService.registerUser(userDto)

        assertEquals(user, result)
    }

    @Test
    fun `registerUser should throw exception when email is already registered`() {
        val userDto = UserCreateDTO(
            "michael",
            "scott",
            "prisonmike@test.com",
            "1725 Slough Avenue, Scranton",
            "Password!1",
            "1234567890123456789012",
            "12345678"
        )

        Mockito.`when`(userRepository.existsByEmail(userDto.email!!)).thenReturn(true)

        assertThrows<Exception> { authService.registerUser(userDto) }
    }

    @Test
    fun `login should return a JWT when credentials are valid`() {
        val loginDTO = LoginRequestDTO("test@test.com", "password")
        val user = UserBuilder().withEmail("test@test.com").withPassword("Password!1").build()

        Mockito.`when`(userRepository.findByEmail(loginDTO.email)).thenReturn(user)
        Mockito.`when`(passwordEncoder.encode(anyString())).thenReturn("Password!1")
        Mockito.`when`(tokenService.generateJWT(user)).thenReturn("jwt")

        val result = authService.login(loginDTO)

        assertEquals("jwt", result)
    }

    @Test
    fun `login should throw exception when user does not exist`() {
        val loginDTO = LoginRequestDTO("nonexistent@test.com", "password")

        Mockito.`when`(userRepository.findByEmail(loginDTO.email)).thenReturn(null)

        assertThrows<Exception> { authService.login(loginDTO) }
    }

    @Test
    fun `registerUser should throw exception when password is weak`() {
        val userDto = UserCreateDTO(
            "michael",
            "scott",
            "prisonmike@test.com",
            "1725 Slough Avenue, Scranton",
            "Password!1",
            "1234567890123456789012",
            "12345678"
        )

        Mockito.`when`(userRepository.existsByEmail(userDto.email!!)).thenReturn(false)

        assertThrows<Exception> { authService.registerUser(userDto) }
    }

    @Test
    fun `registerUser throws exception when email is already registered`() {
        val userDto = UserCreateDTO(
            "michael",
            "scott",
            "prisonmike@test.com",
            "1725 Slough Avenue, Scranton",
            "Password!1",
            "1234567890123456789012",
            "12345678"
        )

        Mockito.`when`(userRepository.existsByEmail(userDto.email!!)).thenReturn(true)

        assertThrows<Exception> { authService.registerUser(userDto) }
    }

}