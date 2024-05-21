
import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl.UserServiceImpl
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [BackendDesappApiApplication::class])
class UserServiceImplTest {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Test
    fun `registerUser returns user when email does not exist`() {
        val userCreateRequest = UserCreateDTO(firstName = "Michael",
            lastName = "Scott",
            email = "michael.scott322@test.com",
            address = "123 Main St",
            password = "Password1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678")
        val result = userService.registerUser(userCreateRequest)
        assertEquals(result.email, result.email)
    }

    @Test
    fun `getUsers returns all users`() {
        val users = userService.getUsers()
        assertNotNull(users)
    }

    @Test
    fun `getUser returns user by id`() {
        val userCreateRequest = UserCreateDTO(firstName = "Michael",
                     lastName = "Scott",
                     email = "michael.scott32@test.com",
                     address = "123 Main St",
                     password = "Password1!",
                     cvu = "1234567890123456789012",
                     walletAddress = "12345678")
        val savedUser = userService.registerUser(userCreateRequest)
        val result = userService.getUser(savedUser.id!!)
        assertEquals(savedUser.email, result.email)
    }
}