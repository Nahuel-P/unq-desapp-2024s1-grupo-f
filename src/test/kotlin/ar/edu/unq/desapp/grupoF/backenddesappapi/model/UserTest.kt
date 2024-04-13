
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock

class UserTest {
    fun aUser(): UserBuilder {
        return UserBuilder()
            .withFirstName("Miguel Angel")
            .withFirstName("Borja")
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

//    @Test
//    fun `a user places a new purchase order`() {
//
//        val user = User(
//            "Carlos",
//            "Gonzalez",
//            "carlos.gonzalez@ejemplo.com.ar",
//            "Avenida Siempre Viva 742",
//            "Contraseña1!",
//            "1234567890123456789012",
//            "12345678"
//        )
//        val cryptocurrencyMock = mock(Cryptocurrency::class.java)
//        val order = user.publishOrder(IntentionType.BUY, cryptocurrencyMock, 0.1, 68064.7)
//
//        Assertions.assertEquals(user, order.ownerUser)
//        Assertions.assertEquals(cryptocurrencyMock, order.cryptocurrency)
//        Assertions.assertEquals(0.1, order.amount)
//        Assertions.assertEquals(68064.7, order.price)
//        Assertions.assertEquals(IntentionType.BUY, order.type)
//
//    }
}