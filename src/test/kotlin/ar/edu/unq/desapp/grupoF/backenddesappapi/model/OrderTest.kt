
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.junit.jupiter.api.Assertions.*
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
        val priceHistory = PriceHistory(CryptoSymbol.BTCUSDT, 2.0)
        return CryptocurrencyBuilder()
            .withName(CryptoSymbol.BTCUSDT)
            .withCreated(LocalDateTime.now())
            .withPriceHistory(mutableListOf(priceHistory))
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
    fun `should not throw exception when order is available`() {
        val order = aOrder().build()

        assertDoesNotThrow {
            order.isAvailable()
        }
    }

    @Test
    fun `should disable order`() {
        val order = aOrder().build()
        order.disable()

        assertFalse(order.isActive)
    }

    @Test
    fun `should not throw exception when order is open and active`() {
        val order = aOrder().build()

        assertDoesNotThrow {
            order.isAvailable()
        }
    }
}