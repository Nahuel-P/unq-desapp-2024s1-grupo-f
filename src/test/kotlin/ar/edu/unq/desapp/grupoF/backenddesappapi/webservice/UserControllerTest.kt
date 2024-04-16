package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class UserControllerTest {

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `registerUser should return registered user`() {
        val userCreateRequest = UserCreateRequestDTO(
            firstName = "Miguel Angel",
            lastName = "Borja",
            email = "el.colibri09@gmail.com",
            address = "Av. Siempre Viva 742",
            password = "Contraseña1!",
            cvu = "1234567890123456789012",
            walletAddress = "12345678"
        )

        val expectedUser = UserBuilder().withFirstName("Miguel Angel")
            .withLastName("Borja")
            .withEmail("el.colibri09@gmail.com")
            .withAddress("Av. Siempre Viva 742")
            .withPassword("Contraseña1!")
            .withCvu("1234567890123456789012")
            .withWalletAddress("12345678").build()

        Mockito.`when`(userService.registerUser(userCreateRequest)).thenReturn(expectedUser)

        val userController = UserController(userService)
        val actualUser = userController.registerUser(userCreateRequest)

        assertEquals(expectedUser, actualUser)
    }
}