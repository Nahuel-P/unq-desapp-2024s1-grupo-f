package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IAuthService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.LoginRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authService: IAuthService

    private val objectMapper: ObjectMapper = jacksonObjectMapper()


    @Test
    fun `registerUser returns BAD_REQUEST status when registration fails`() {
        val userCreateRequest = UserCreateDTO(
            "michael",
            "scott",
            "prisonmike@test.com",
            "1725 Slough Avenue, Scranton",
            "Password!1",
            "1234567890123456789012",
            "12345678"
        )

        `when`(authService.registerUser(userCreateRequest)).thenThrow(RuntimeException("Registration failed"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `login returns OK status when login is successful`() {
        val loginRequestDTO = LoginRequestDTO("username", "password")
        val loginRequestJson = objectMapper.writeValueAsString(loginRequestDTO)

        val user = UserBuilder()
            .withFirstName("michael")
            .withLastName("scott")
            .withEmail("prisonmike@test.com")
            .withAddress("1725 Slough Avenue, Scranton")
            .withPassword("Password!1")
            .withCvu("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()
        user.id = 1L

        `when`(authService.login(loginRequestDTO)).thenReturn(UserMapper.toDTO(user).toString())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `login returns BAD_REQUEST status when login fails`() {
        val loginRequest = LoginRequestDTO("prisonmike@test.com", "Password!1")

        `when`(authService.login(loginRequest)).thenThrow(RuntimeException("Login failed"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}