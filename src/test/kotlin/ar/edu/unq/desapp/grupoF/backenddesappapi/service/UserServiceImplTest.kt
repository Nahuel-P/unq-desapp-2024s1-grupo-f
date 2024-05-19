
import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl.UserServiceImpl
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
        val user = User(email = "test333@test.com")
        val result = userService.registerUser(user)
        assertEquals(user.email, result.email)
    }

    @Test
    fun `getUsers returns all users`() {
        val users = userService.getUsers()
        assertNotNull(users)
    }

    @Test
    fun `getUser returns user by id`() {
        val user = User(email = "test@test.com")
        val savedUser = userService.registerUser(user)
        val result = userService.getUser(savedUser.id!!)
        assertEquals(savedUser.email, result.email)
    }
}