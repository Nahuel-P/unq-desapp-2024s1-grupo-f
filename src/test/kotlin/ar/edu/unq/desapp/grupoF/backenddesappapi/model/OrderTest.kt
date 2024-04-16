
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OrderTest {

    @Test
    fun `should create a order when it has valid data`() {
        org.junit.jupiter.api.assertDoesNotThrow { aOrder().build() }
    }

    @Test
    fun `builds order with an owner`() {
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
    fun `builds buy order`() {
        val order = aOrder().withType(IntentionType.BUY).build()
        assertTrue(order.isBuyOrder())
    }

    @Test
    fun `builds sell order`() {
        val order = aOrder().withType(IntentionType.SELL).build()
        assertTrue(order.isSellOrder())
    }

    @Test
    fun `order with valid data is available`() {
        val order = aOrder().build()
        order.disable()
        assertFalse(order.isActive)
    }


    @Test
    fun `should disable order`() {
        val order = aOrder().build()
        order.disable()
        assertFalse(order.isActive())
    }



}