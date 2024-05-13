
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.UserController
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

class UserControllerTest {

    private val userService: IUserService = mock(IUserService::class.java)

    @Test
    fun `registerUser returns success message when valid UserCreateRequestDTO is provided`() {
        val userCreateRequest = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "validPassword!1",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val response = mapOf("message" to "User registration successful")

        `when`(userService.registerUser(userCreateRequest)).thenReturn(Unit)

        val userController = UserController(userService)
        val actualResponse = userController.registerUser(userCreateRequest)

        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(response, actualResponse.body)
    }

    @Test
    fun `registerUser returns error message when exception is thrown`() {
        val userCreateRequest = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "validPassword!1",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val exceptionMessage = "Error registering user"
        `when`(userService.registerUser(userCreateRequest)).thenThrow(RuntimeException(exceptionMessage))

        val userController = UserController(userService)
        val actualResponse = userController.registerUser(userCreateRequest)

        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.statusCode)
        assertEquals(mapOf("message" to exceptionMessage), actualResponse.body)
    }
}