
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OrderTest {

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

    fun aCryptocurrency(): CryptocurrencyBuilder {
        return CryptocurrencyBuilder()
            .withName("BTCUSDT")
            .withCreated(LocalDateTime.now())
    }

    fun aOrder(): OrderBuilder {
        return OrderBuilder()
            .withOwnerUser(aUser().build())
            .withCryptocurrency(aCryptocurrency().build())
            .withAmount(10.0)
            .withPrice(50000.0)
            .withType(IntentionType.BUY)
    }
    @Test
    fun `builds order with correct owner`() {
        val owner = aUser().build()
        val order = aOrder().withOwnerUser(owner).build()
        assertEquals(owner, order.ownerUser)
    }

    @Test
    fun `builds order with correct cryptocurrency`() {
        val cryptocurrency = aCryptocurrency().build()
        val order = aOrder().withCryptocurrency(cryptocurrency).build()
        assertEquals(cryptocurrency, order.cryptocurrency)
    }

    @Test
    fun `builds order with correct amount`() {
        val amount = 10.0
        val order = aOrder().withAmount(amount).build()
        assertEquals(amount, order.amount)
    }

    @Test
    fun `builds order with correct price`() {
        val price = 50000.0
        val order = aOrder().withPrice(price).build()
        assertEquals(price, order.price)
    }

    @Test
    fun `builds order with correct type`() {
        val order = aOrder().withType(IntentionType.BUY).build()
        assertEquals(IntentionType.BUY, order.type)
    }

    @Test
    fun `throws exception when building order without owner`() {
        val orderBuilder = OrderBuilder().withCryptocurrency(aCryptocurrency().build()).withAmount(10.0).withPrice(50000.0).withType(IntentionType.BUY)
        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
    }

    @Test
    fun `throws exception when building order without cryptocurrency`() {
        val orderBuilder = OrderBuilder().withOwnerUser(aUser().build()).withAmount(10.0).withPrice(50000.0).withType(IntentionType.BUY)
        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
    }

    @Test
    fun `throws exception when building order without amount`() {
        val orderBuilder = OrderBuilder().withOwnerUser(aUser().build()).withCryptocurrency(aCryptocurrency().build()).withPrice(50000.0).withType(IntentionType.BUY)
        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
    }

    @Test
    fun `throws exception when building order without price`() {
        val orderBuilder = OrderBuilder().withOwnerUser(aUser().build()).withCryptocurrency(aCryptocurrency().build()).withAmount(10.0).withType(IntentionType.BUY)
        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
    }

    @Test
    fun `throws exception when building order without type`() {
        val orderBuilder = OrderBuilder().withOwnerUser(aUser().build()).withCryptocurrency(aCryptocurrency().build()).withAmount(10.0).withPrice(50000.0)
        assertThrows(IllegalArgumentException::class.java) { orderBuilder.build() }
    }
}