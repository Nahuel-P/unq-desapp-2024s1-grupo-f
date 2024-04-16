package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceImplTest {
    @Test
    fun `should register user with provided email`() {
        val userService = UserServiceImpl()
        val userCreateRequest = UserCreateRequestDTO().apply { email = "test@example.com" }

        val user = userService.registerUser(userCreateRequest)

        assertEquals("test@example.com", user.email)
    }

    @Test
    fun `should return different user instances for different registrations`() {
        val userService = UserServiceImpl()
        val userCreateRequest1 = UserCreateRequestDTO().apply { email = "test1@example.com" }
        val userCreateRequest2 = UserCreateRequestDTO().apply { email = "test2@example.com" }

        val user1 = userService.registerUser(userCreateRequest1)
        val user2 = userService.registerUser(userCreateRequest2)

        assertNotSame(user1, user2)
        assertEquals("test1@example.com", user1.email)
        assertEquals("test2@example.com", user2.email)
    }

    @Test
    fun `should throw exception when email is not provided`() {
        val userService = UserServiceImpl()
        val userCreateRequest = UserCreateRequestDTO()

        val exception = assertThrows<IllegalArgumentException> {
            userService.registerUser(userCreateRequest)
        }

        assertEquals("Email must not be null or empty", exception.message)
    }
}