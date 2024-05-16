package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

class UserControllerTest {
    private val userService: IUserService = mock(IUserService::class.java)

    @Test
    fun `getUsers returns all registered users`() {
        val users = listOf(UserBuilder().withFirstName("Michael").withLastName("Scott").withEmail("mscott@gmail.com").withAddress("1725 Slough Avenue").withPassword("P45sword!1").withCvu("1234567890123456789012").withWalletAddress("12345678").build())
        `when`(userService.getUsers()).thenReturn(users)

        val userController = UserController(userService)
        val actualResponse = userController.getUsers()

        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(users, actualResponse.body)
    }

    @Test
    fun `getUsersByID returns user with given id`() {
        val user = UserBuilder().withFirstName("Michael").withLastName("Scott").withEmail("mscott@gmail.com").withAddress("1725 Slough Avenue").withPassword("P45sword!1").withCvu("1234567890123456789012").withWalletAddress("12345678").build()
        `when`(userService.findUser(1)).thenReturn(user)

        val userController = UserController(userService)
        val actualResponse = userController.getUserByID(1)

        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(user, actualResponse.body)
    }

    @Test
    fun `getUsersByID returns error message when user with given id does not exist`() {
        val exceptionMessage = "User not found"
        `when`(userService.findUser(3)).thenThrow(RuntimeException(exceptionMessage))

        val userController = UserController(userService)

        val exception = assertThrows<RuntimeException> {
            userController.getUserByID(3)
        }

        assertEquals(exceptionMessage, exception.message)
    }
}