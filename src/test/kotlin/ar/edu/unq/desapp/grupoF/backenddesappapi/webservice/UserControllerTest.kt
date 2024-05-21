package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [BackendDesappApiApplication::class])
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var userService: IUserService

    // @Test
    // fun shouldRegisterAUser() {
    //     val userData = UserCreateRequestDTO(
    //         firstName = "Michael",
    //         lastName = "Scott",
    //         email = "michael.scott232332@test.com",
    //         address = "123 Main St",
    //         password = "Password1!",
    //         cvu = "1234567890123456789012",
    //         walletAddress = "12345678"
    //     )
    //     val parsedUserData = ObjectMapper().writeValueAsString(userData)

    //     mockMvc.perform(
    //         MockMvcRequestBuilders.post("/user/registerUser")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(parsedUserData)
    //     ).andExpect(status().isOk)

    // }

    @Test
    fun `getUsers returns all users`() {
        val users = listOf(User(email = "michael.scott@test.com"))
        `when`(userService.getUsers()).thenReturn(users)

        mockMvc.perform(get("/user/users"))
            .andExpect(status().isOk)
    }

    @Test
    fun `getUserByID returns user when user with given id exists`() {
        val user = User(email = "michael.scott@test.com")
        user.id = 10L
        `when`(userService.getUser(user.id!!)).thenReturn(user)

        mockMvc.perform(get("/user/${user.id}"))
            .andExpect(status().isOk)
    }
}