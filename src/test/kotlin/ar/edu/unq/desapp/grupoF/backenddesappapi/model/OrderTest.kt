import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aBTCUSDT
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aBuyOrder
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class OrderTest {

    @Test
    fun `should create a order when it has valid data`() {
        org.junit.jupiter.api.assertDoesNotThrow { OrderBuilder().build() }
    }

    @Test
    fun `builds order with an owner`() {
        val owner = aUser().build()
        val order = OrderBuilder().withOwnerUser(owner).build()
        assertEquals(owner, order.ownerUser)
    }

    @Test
    fun `builds order with correct cryptocurrency`() {
        val cryptocurrency = aBTCUSDT().build()
        val order = OrderBuilder().withCryptocurrency(cryptocurrency).build()
        assertEquals(cryptocurrency, order.cryptocurrency)
    }

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
    fun `entryTime is set when order is created`() {
        val order = OrderBuilder().build()
        assertNotNull(order.entryTime)
        assertTrue(order.entryTime.isBefore(LocalDateTime.now().plusSeconds(1)))
    }

    @Test
    fun `builds buy order`() {
        val order = OrderBuilder().withType(IntentionType.BUY).build()
        assertTrue(order.isBuyOrder())
    }

    @Test
    fun `should return false when order is not sell order`() {
        val order = OrderBuilder().withType(IntentionType.BUY).build()
        assertFalse(order.isSellOrder())
    }

    @Test
    fun `builds sell order`() {
        val order = OrderBuilder().withType(IntentionType.SELL).build()
        assertTrue(order.isSellOrder())
    }

    @Test
    fun `should return false when order is not buy order`() {
        val order = OrderBuilder().withType(IntentionType.SELL).build()
        assertFalse(order.isBuyOrder())
    }

    @Test
    fun `order with valid data is active`() {
        val order = OrderBuilder().build()
        assertTrue(order.isActive)
    }

    @Test
    fun `order with valid data is transferable`() {
        val order = OrderBuilder().build()
        assertTrue(order.isTransferable())
    }

    @Test
    fun `should disable an order to make it inactive`() {
        val order = OrderBuilder().build()
        order.disable()
        assertFalse(order.isActive)
    }

    @Test
    fun `an inactive order is not transferable`() {
        val order = OrderBuilder().build()
        order.disable()
        assertFalse(order.isTransferable())
    }

    @Test
    fun `should return false when order is not finished`() {
        val order = OrderBuilder().build()
        assertFalse(order.isFinished())
    }

    @Test
    fun `when closing an order, its status becomes finished`() {
        val order = OrderBuilder().build()
        order.close()
        assertTrue(order.isFinished())
    }

    @Test
    fun `a closed order is not transferable`() {
        val order = OrderBuilder().build()
        order.close()
        assertFalse(order.isTransferable())
    }

    @Test
    fun `return false when order is reset after being finished`() {
        val order = OrderBuilder().build()
        order.close()
        order.reset()
        assertFalse(order.isFinished())
    }

    @Test
    fun `return true when order price is above market price with margin`() {
        val order = OrderBuilder().withPrice(110.0).withCryptocurrency(aBTCUSDT().withPrice(100.0).build()).build()
        assertTrue(order.isAboveMarginPrice(5.0))
    }

    @Test
    fun `return false when order price is not above market price with margin`() {
        val order = OrderBuilder().withPrice(104.9).withCryptocurrency(aBTCUSDT().withPrice(100.0).build()).build()
        assertFalse(order.isAboveMarginPrice(5.0))
    }

    @Test
    fun `return true when order price is below market price with margin`() {
        val order = OrderBuilder().withPrice(95.0).withCryptocurrency(aBTCUSDT().withPrice(100.0).build()).build()
        assertTrue(order.isBelowMarginPrice(5.0))
    }

    @Test
    fun `return false when order price is not below market price with margin`() {
        val order = OrderBuilder().withPrice(95.1).withCryptocurrency(aBTCUSDT().withPrice(100.0).build()).build()
        assertFalse(order.isBelowMarginPrice(5.0))
    }
}

