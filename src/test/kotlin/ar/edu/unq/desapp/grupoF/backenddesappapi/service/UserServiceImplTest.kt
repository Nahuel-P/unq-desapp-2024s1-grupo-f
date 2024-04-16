
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.UserServiceImpl
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserServiceImplTest {

    private val userService = UserServiceImpl()
    @Test
    fun `registerUser should return error message when email is null`() {
        val userCreateRequest = UserCreateRequestDTO(
            firstName = "Michael",
            lastName = "Scott",
            email = null,
            address = "1725 Slough Avenue, Scranton",
            password = "Password1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val expectedResponse = "{\"message\":\"Email must not be null or empty\"}"

        val actualResponse = userService.registerUser(userCreateRequest)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `registerUser should return error message when email is empty`() {
        val userCreateRequest = UserCreateRequestDTO(
            firstName = "Michael",
            lastName = "Scott",
            email = "",
            address = "1725 Slough Avenue, Scranton",
            password = "Password1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val expectedResponse = "{\"message\":\"Email must not be null or empty\"}"

        val actualResponse = userService.registerUser(userCreateRequest)

        assertEquals(expectedResponse, actualResponse)
    }
}