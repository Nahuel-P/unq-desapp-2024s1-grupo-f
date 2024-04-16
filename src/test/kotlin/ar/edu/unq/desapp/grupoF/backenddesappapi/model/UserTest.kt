import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class UserTest {
    fun aUser(): UserBuilder {
        return UserBuilder()
            .withFirstName("Miguel Angel")
            .withLastName("Borja")
            .withEmail("el.colibri09@gmail.com")
            .withAddress("Av. Siempre Viva 742")
            .withPassword("Contraseña1!")
            .withCvu("1234567890123456789012")
            .withWalletAddress("12345678")
    }

    @Test
    fun `should create a user when it has valid data`() {
        assertDoesNotThrow { aUser().build() }
    }

    @Test
    fun `throw exception for invalid minimum first name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withFirstName("Mi").build()
        }
    }

    @Test
    fun `throw exception  for exceeding maximum first name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withFirstName("el nombre que quiero cargar es mas largo de lo que se permite").build()
        }
    }

    @Test
    fun `throw exception for invalid minimum last name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withLastName("Bo").build()
        }
    }

    @Test
    fun `throw exception  for exceeding maximum last name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withLastName("el apellido que quiero cargar es mas largo de lo que se permite").build()
        }
    }

    @Test
    fun `invalid email format throws exception`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withEmail("el.colibri").build()
        }
    }

    @Test
    fun `throw exception for invalid minimum adress length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withAddress("Av. ").build()
        }
    }

    @Test
    fun `throw exception for exceeding maximum adress length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withAddress("Av. la calle que estoy intentando ingresar es mas larga de lo permitido").build()
        }
    }

    @Test
    fun `invalid password format throws exception`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withPassword("1234").build()
        }
    }

    @Test
    fun `throw exception for invalid minimum cvu length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withCvu("752").build()
        }
    }

    @Test
    fun `throw exception for exceeding maximum cvu length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withCvu("7529888111100012745826123").build()
        }
    }

    @Test
    fun `throw exception for invalid minimum wallet length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withCvu("12").build()
        }
    }

    @Test
    fun `throw exception for exceeding maximum wallet length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withCvu("12345678912121212").build()
        }
    }

    @Test
    fun `should decrease reputation by 20`() {
        val user = aUser().build()
        user.decreaseScore()
        assertEquals(-20, user.score)
    }

    @Test
    fun `should increase reputation by given increment`() {
        val user = aUser().build()
        user.increaseScore(10)
        assertEquals(10, user.score)
    }

    @Test
    fun `should increase reputation by given increment multiple times`() {
        val user = aUser().build()
        for (i in 1..10) {
            user.increaseScore(10)
        }
        assertEquals(100, user.score)
    }

    @Test
    fun `should increase transactions by 1`() {
        val user = aUser().build()
        user.increaseTransactions()
        assertEquals(1, user.successfulTransaction)
    }

    @Test
    fun `user reputation is 0 when no transactions`() {
        val user = aUser().build()
        assertEquals(0, user.reputation())
    }

    @Test
    fun `user reputation is 10 when 1 transaction and 10 score`() {
        val user = aUser().build()
        user.increaseScore(10).increaseTransactions()
        assertEquals(10, user.reputation())
    }
}