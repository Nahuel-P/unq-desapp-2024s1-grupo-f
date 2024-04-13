package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.persitence.IUserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private lateinit var userRepository: IUserRepository

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Test
    fun `registerUser throws IllegalArgumentException when provided invalid email`() {
        try {
            val user = User(
                "Juan",
                "Lopez",
                "invalid email",
                "Calle Falsa 123",
                "Password1!",
                "1234567890123456789012",
                "12345678"
            )

            `when`(userRepository.registerUser(user)).thenReturn(user)

            userService.registerUser(
                "Juan",
                "Lopez",
                "invalid email",
                "Calle Falsa 123",
                "Password1!",
                "1234567890123456789012",
                "12345678"
            )
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {
            assertEquals("Invalid email format", e.message)
        }
    }

    @Test
    fun `registerUser throws IllegalArgumentException when provided invalid password`() {
        val user = User(
            "Juan",
            "Lopez",
            "juan.lopez@example.com",
            "Calle Falsa 123",
            "Password1!",
            "1234567890123456789012",
            "12345678"
        )

        `when`(userRepository.registerUser(user)).thenThrow(IllegalArgumentException("Invalid password"))

        assertThrows<IllegalArgumentException> {
            userService.registerUser(
                "Juan",
                "Lopez",
                "juan.lopez@example.com",
                "Calle Falsa 123",
                "password",
                "1234567890123456789012",
                "12345678"
            )
        }
    }
}