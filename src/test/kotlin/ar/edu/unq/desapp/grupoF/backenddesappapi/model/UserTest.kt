
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
    }

    @Test
    fun `throw exception for invalid minimum first name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withFirstName("as").build()
        }
    }

    @Test
    fun `throw exception  for exceeding maximum first name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withFirstName("gIHqnJsKpFOkwDfUMoBrTLCNcWvXbteG").build()
        }
    }

    @Test
    fun `throw exception for invalid minimum last name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withLastName("sa").build()
        }
    }

    @Test
    fun `throw exception  for exceeding maximum last name length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withLastName("gIHqnJsKpFOkwDfUMoBrTLCNcWvXbteG").build()
        }
    }

    @Test
    fun `invalid email format throws exception`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withEmail("carlos.gonzalez").build()
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
    fun `throw exception  for exceeding maximum adress length`() {
        assertThrows<IllegalArgumentException> {
            aUser()
                .withAddress("Av. la calle que estoy intentando ingresar es mas larga de lo permitido").build()
        }
    }
//    @Test
//    fun `invalid address length throws exception`() {
//        assertThrows<IllegalArgumentException> {
//            User(
//                "Carlos",
//                "Gonzalez",
//                "carlos.gonzalez@ejemplo.com.ar",
//                "Avenida 7",
//                "Contraseña1!",
//                "1234567890123456789012",
//                "12345678"
//            )
//        }
//    }
//
//    @Test
//    fun `invalid password format throws exception`() {
//        assertThrows<IllegalArgumentException> {
//            User(
//                "Carlos",
//                "Gonzalez",
//                "carlos.gonzalez@ejemplo.com.ar",
//                "Avenida Siempre Viva 742",
//                "contraseña",
//                "1234567890123456789012",
//                "12345678"
//            )
//        }
//    }
//
//    @Test
//    fun `invalid cvu length throws exception`() {
//        assertThrows<IllegalArgumentException> {
//            User(
//                "Carlos",
//                "Gonzalez",
//                "carlos.gonzalez@ejemplo.com.ar",
//                "Avenida Siempre Viva 742",
//                "Contraseña1!",
//                "123456789012345678901",
//                "12345678"
//            )
//        }
//    }
//
//    @Test
//    fun `invalid wallet length throws exception`() {
//        assertThrows<IllegalArgumentException> {
//            User(
//                "Carlos",
//                "Gonzalez",
//                "carlos.gonzalez@ejemplo.com.ar",
//                "Avenida Siempre Viva 742",
//                "Contraseña1!",
//                "1234567890123456789012",
//                "1234567"
//            )
//        }
//    }
//
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