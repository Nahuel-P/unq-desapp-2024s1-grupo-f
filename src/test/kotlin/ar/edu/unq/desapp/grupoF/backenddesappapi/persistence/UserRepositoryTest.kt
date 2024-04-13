package ar.edu.unq.desapp.grupoF.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.persitence.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserRepositoryTest {

    private val userRepository = UserRepository()

    @Test
    fun `registerUser stores user in users HashMap with correct ID`() {
        val user = User("Juan", "Lopez", "juan.lopez@example.com", "Calle Falsa 123", "Password1!", "1234567890123456789012", "12345678")

        userRepository.registerUser(user)

        val users = userRepository.getUsers()
        val storedUser = users[user.id]

        assertEquals(user, storedUser)
    }

    @Test
    fun `registerUser returns UserExchange object with correct ID`() {
        val user = User("Juan", "Lopez", "juan.lopez@example.com", "Calle Falsa 123", "Password1!", "1234567890123456789012", "12345678")

        val returnedUser = userRepository.registerUser(user)

        assertEquals(user.id, returnedUser.id)
    }
}