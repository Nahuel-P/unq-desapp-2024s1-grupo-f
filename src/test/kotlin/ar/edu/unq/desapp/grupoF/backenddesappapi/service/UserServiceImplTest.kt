package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class UserServiceImplTest {

    private val userRepository: UserRepository = Mockito.mock(UserRepository::class.java)
    private val userService = UserServiceImpl(userRepository)

    @Test
    fun `registerUser throws exception when email already exists`() {
        val user = UserBuilder().withFirstName("Michael").withLastName("Scott").withEmail("mscott@gmail.com").withAddress("1725 Slough Avenue").withPassword("P45sword!1").withCvu("1234567890123456789012").withWalletAddress("12345678").build()
        `when`(userRepository.existsByEmail(user.email!!)).thenReturn(true)

        assertThrows<Exception> {
            userService.registerUser(user)
        }
    }

    @Test
    fun `registerUser returns user when email does not exist`() {
        val user = UserBuilder().withFirstName("Michael").withLastName("Scott").withEmail("mscott@gmail.com").withAddress("1725 Slough Avenue").withPassword("P45sword!1").withCvu("1234567890123456789012").withWalletAddress("12345678").build()
        `when`(userRepository.existsByEmail(user.email!!)).thenReturn(false)
        `when`(userRepository.save(user)).thenReturn(user)

        val result = userService.registerUser(user)

        assertEquals(user, result)
    }

    @Test
    fun `getUsers returns all users`() {
        val user = UserBuilder().withFirstName("Michael").withLastName("Scott").withEmail("mscott@gmail.com").withAddress("1725 Slough Avenue").withPassword("P45sword!1").withCvu("1234567890123456789012").withWalletAddress("12345678").build()
        `when`(userRepository.findAll()).thenReturn(listOf(user))

        val result = userService.getUsers()

        assertEquals(listOf(user), result)
    }

    @Test
    fun `findUser throws exception when user does not exist`() {
        val id = 1L
        `when`(userRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows<Exception> {
            userService.findUser(id)
        }
    }

}