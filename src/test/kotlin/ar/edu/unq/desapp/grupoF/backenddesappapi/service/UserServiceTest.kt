import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.IUserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UserServiceTest {

    @Mock
    private lateinit var userRepository: IUserRepository

    @InjectMocks
    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `registerUser saves user to repository when provided valid data`() {
        val userCaptor = ArgumentCaptor.forClass(UserExchange::class.java)
        val user = UserExchange("Juan", "Lopez", "juan.lopez@example.com", "Calle Falsa 123", "Password1!", "1234567890123456789012", "12345678")

        `when`(userRepository.save(userCaptor.capture())).thenReturn(user)

        val result = userService.registerUser("Juan", "Lopez", "juan.lopez@example.com", "Calle Falsa 123", "Password1!", "1234567890123456789012", "12345678")

        verify(userRepository).save(userCaptor.capture())
        assertEquals(user, result)
    }

    @Test
    fun `registerUser throws IllegalArgumentException when provided invalid email`() {
        val userCaptor = ArgumentCaptor.forClass(UserExchange::class.java)

        `when`(userRepository.save(userCaptor.capture())).thenThrow(IllegalArgumentException("Invalid email"))

        assertThrows<IllegalArgumentException> {
            userService.registerUser("Juan", "Lopez", "invalid email", "Calle Falsa 123", "Password1!", "1234567890123456789012", "12345678")
        }
    }

    @Test
    fun `registerUser throws IllegalArgumentException when provided invalid password`() {
        val userCaptor = ArgumentCaptor.forClass(UserExchange::class.java)

        `when`(userRepository.save(userCaptor.capture())).thenThrow(IllegalArgumentException("Password must contain at least 1 lowercase letter, 1 uppercase letter, 1 special character and be at least 6 characters long"))

        assertThrows<IllegalArgumentException> {
            userService.registerUser("Juan", "Lopez", "juan.lopez@example.com", "Calle Falsa 123", "password", "1234567890123456789012", "12345678")
        }
    }
}