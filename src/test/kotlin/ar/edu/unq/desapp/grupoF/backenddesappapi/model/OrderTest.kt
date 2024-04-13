import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class OrderBuilderTest {

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
    fun `builds order with correct owner`() {
        val owner = aUser().build()
        val order = OrderBuilder().withOwnerUser(owner).build()
        assertEquals(owner, order.ownerUser)
    }

//    @Test
//    fun `builds order with correct cryptocurrency`() {
//        val cryptocurrency = Cryptocurrency("Bitcoin", "BTC")
//        val order = OrderBuilder().withCryptocurrency(cryptocurrency).build()
//        assertEquals(cryptocurrency, order.cryptocurrency)
//    }

    @Test
    fun `builds order with correct amount`() {
        val amount = 10.0
        val order = OrderBuilder().withAmount(amount).build()
        assertEquals(amount, order.amount)
    }

    @Test
    fun `builds order with correct price`() {
        val price = 50000.0
        val order = OrderBuilder().withPrice(price).build()
        assertEquals(price, order.price)
    }

    @Test
    fun `builds order with correct type`() {
        val order = OrderBuilder().withType(IntentionType.BUY).build()
        assertEquals(IntentionType.BUY, order.type)
    }

//    @Test
//    fun `throws exception when building order without owner`() {
//        val orderBuilder = OrderBuilder().withCryptocurrency(Cryptocurrency("Bitcoin", "BTC")).withAmount(10.0).withPrice(50000.0).withType(IntentionType.BUY)
//        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
//    }

//    @Test
//    fun `throws exception when building order without cryptocurrency`() {
//        val orderBuilder = OrderBuilder().withOwnerUser(User("John", "Doe", "john.doe@example.com")).withAmount(10.0).withPrice(50000.0).withType(IntentionType.BUY)
//        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
//    }
//
//    @Test
//    fun `throws exception when building order without amount`() {
//        val orderBuilder = OrderBuilder().withOwnerUser(User("John", "Doe", "john.doe@example.com")).withCryptocurrency(Cryptocurrency("Bitcoin", "BTC")).withPrice(50000.0).withType(IntentionType.BUY)
//        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
//    }
//
//    @Test
//    fun `throws exception when building order without price`() {
//        val orderBuilder = OrderBuilder().withOwnerUser(User("John", "Doe", "john.doe@example.com")).withCryptocurrency(Cryptocurrency("Bitcoin", "BTC")).withAmount(10.0).withType(IntentionType.BUY)
//        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
//    }
//
//    @Test
//    fun `throws exception when building order without type`() {
//        val orderBuilder = OrderBuilder().withOwnerUser(User("John", "Doe", "john.doe@example.com")).withCryptocurrency(Cryptocurrency("Bitcoin", "BTC")).withAmount(10.0).withPrice(50000.0)
//        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
//    }
}