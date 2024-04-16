package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceImplTest {

    @Test
    fun `registerUser should throw IllegalArgumentException when UserCreateRequestDTO with null email is provided`() {
        val userService = UserServiceImpl()
        val userCreateRequest = UserCreateRequestDTO().apply { email = null }

        assertThrows(IllegalArgumentException::class.java) {
            userService.registerUser(userCreateRequest)
        }
    }

    @Test
    fun `registerUser should throw IllegalArgumentException when UserCreateRequestDTO with empty email is provided`() {
        val userService = UserServiceImpl()
        val userCreateRequest = UserCreateRequestDTO().apply { email = "" }

        assertThrows(IllegalArgumentException::class.java) {
            userService.registerUser(userCreateRequest)
        }
    }
}