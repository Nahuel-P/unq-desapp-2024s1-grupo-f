import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class UserExchangeTest {

    @Test
    fun `valid user exchange creation`() {
        val user = UserExchange(
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
            UserExchange(
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
            UserExchange(
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
            UserExchange(
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
            UserExchange(
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
            UserExchange(
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
            UserExchange(
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
            UserExchange(
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
        val userMock = mock(UserExchange::class.java)
        val cryptocurrencyMock = mock(Cryptocurrency::class.java)

        userMock.publishOrder(IntentionType.BUY, cryptocurrencyMock, 0.1, 68064.7)
        verify(userMock).publishOrder(IntentionType.BUY, cryptocurrencyMock, 0.1, 68064.7)
    }
}