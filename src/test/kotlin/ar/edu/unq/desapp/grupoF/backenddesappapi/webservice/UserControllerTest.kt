
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.UserController
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

class UserControllerTest {

    private val userService: UserService = mock(UserService::class.java)

    @Test
    fun `registerUser should return UserResponseDTO when valid UserCreateRequestDTO is provided`() {
        val userCreateRequest = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "valid",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val userResponseDTO = UserResponseDTO(
            firstName = "Miguel Angel",
            email = "el.colibri09@gmail.com",
        )

        `when`(userService.registerUser(userCreateRequest)).thenReturn(userResponseDTO)

        val userController = UserController(userService)
        val actualResponse = userController.registerUser(userCreateRequest)

        assertEquals(HttpStatus.OK, actualResponse.statusCode)
        assertEquals(userResponseDTO, actualResponse.body)
    }

}