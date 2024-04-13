
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock

class UserTest {

    @Test
    fun `valid user exchange creation`() {
        val user = User(
            "Carlos",
            "Gonzalez",
            "carlos.gonzalez@ejemplo.com.ar",
            "Avenida Siempre Viva 742",
            "Contraseña1!",
            "1234567890123456789012",
            "12345678"
        )

        Assertions.assertEquals("Carlos", user.firstName)
        Assertions.assertEquals("Gonzalez", user.lastName)
        Assertions.assertEquals("carlos.gonzalez@ejemplo.com.ar", user.email)
        Assertions.assertEquals("Avenida Siempre Viva 742", user.address)
        Assertions.assertEquals("Contraseña1!", user.password)
    }

    @Test
    fun `invalid first name length throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Ca",
                "Gonzalez",
                "carlos.gonzalez@ejemplo.com.ar",
                "Avenida Siempre Viva 742",
                "Contraseña1!",
                "1234567890123456789012",
                "12345678"
            )
        }
    }

    @Test
    fun `invalid last name length throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Carlos",
                "Go",
                "carlos.gonzalez@ejemplo.com.ar",
                "Avenida Siempre Viva 742",
                "Contraseña1!",
                "1234567890123456789012",
                "12345678"
            )
        }
    }

    @Test
    fun `invalid email format throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Carlos",
                "Gonzalez",
                "carlos.gonzalez",
                "Avenida Siempre Viva 742",
                "Contraseña1!",
                "1234567890123456789012",
                "12345678"
            )
        }
    }

    @Test
    fun `invalid address length throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Carlos",
                "Gonzalez",
                "carlos.gonzalez@ejemplo.com.ar",
                "Avenida 7",
                "Contraseña1!",
                "1234567890123456789012",
                "12345678"
            )
        }
    }

    @Test
    fun `invalid password format throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Carlos",
                "Gonzalez",
                "carlos.gonzalez@ejemplo.com.ar",
                "Avenida Siempre Viva 742",
                "contraseña",
                "1234567890123456789012",
                "12345678"
            )
        }
    }

    @Test
    fun `invalid cvu length throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Carlos",
                "Gonzalez",
                "carlos.gonzalez@ejemplo.com.ar",
                "Avenida Siempre Viva 742",
                "Contraseña1!",
                "123456789012345678901",
                "12345678"
            )
        }
    }

    @Test
    fun `invalid wallet length throws exception`() {
        assertThrows<IllegalArgumentException> {
            User(
                "Carlos",
                "Gonzalez",
                "carlos.gonzalez@ejemplo.com.ar",
                "Avenida Siempre Viva 742",
                "Contraseña1!",
                "1234567890123456789012",
                "1234567"
            )
        }
    }

    @Test
    fun `a user places a new purchase order`() {

        val user = User(
            "Carlos",
            "Gonzalez",
            "carlos.gonzalez@ejemplo.com.ar",
            "Avenida Siempre Viva 742",
            "Contraseña1!",
            "1234567890123456789012",
            "12345678"
        )
        val cryptocurrencyMock = mock(Cryptocurrency::class.java)
        val order = user.publishOrder(IntentionType.BUY, cryptocurrencyMock, 0.1, 68064.7)

        Assertions.assertEquals(user, order.ownerUser)
        Assertions.assertEquals(cryptocurrencyMock, order.cryptocurrency)
        Assertions.assertEquals(0.1, order.amount)
        Assertions.assertEquals(68064.7, order.price)
        Assertions.assertEquals(IntentionType.BUY, order.type)

    }
}