package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice
import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus

@SpringBootTest(classes = [BackendDesappApiApplication::class])
class UserControllerTest {

    @MockBean
    private lateinit var userService: IUserService
    @Autowired
    private lateinit var userController: UserController
 
    @Test
    fun `getUsers returns all users`() {
        val users = listOf(User(email = "michael.scott@test.com"))
        `when`(userService.getUsers()).thenReturn(users)

        val response = userController.getUsers()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(users, response.body)
    }

    @Test
    fun `getUserByID returns user when user with given id exists`() {
        val user = User(email = "michael.scott@test.com")
        user.id = 10L
        `when`(userService.getUser(user.id!!)).thenReturn(user)

        val response = userController.getUserByID(user.id!!)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
    }

}