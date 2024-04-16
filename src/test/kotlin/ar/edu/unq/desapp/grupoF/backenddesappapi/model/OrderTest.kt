import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aCryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aOrder
import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aUser
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
    fun `entryTime is set when order is created`() {
        val order = aOrder().build()
        assertNotNull(order.entryTime)
        assertTrue(order.entryTime.isBefore(LocalDateTime.now().plusSeconds(1)))
    }

    @Test
    fun `builds buy order`() {
        val order = aOrder().withType(IntentionType.BUY).build()
        assertTrue(order.isBuyOrder())
    }

    @Test
    fun `should return false when order is not sell order`() {
        val order = aOrder().withType(IntentionType.BUY).build()
        assertFalse(order.isSellOrder())
    }

    @Test
    fun `builds sell order`() {
        val order = aOrder().withType(IntentionType.SELL).build()
        assertTrue(order.isSellOrder())
    }

    @Test
    fun `should return false when order is not buy order`() {
        val order = aOrder().withType(IntentionType.SELL).build()
        assertFalse(order.isBuyOrder())
    }

    @Test
    fun `order with valid data is active`() {
        val order = aOrder().build()
        assertTrue(order.isActive())
    }

    @Test
    fun `order with valid data is transactable`() {
        val order = aOrder().build()
        assertTrue(order.isTransactable())
    }

    @Test
    fun `should disable an order to make it inactive`() {
        val order = aOrder().build()
        order.disable()
        assertFalse(order.isActive())
    }

    @Test
    fun `an inactive order is not transactable`() {
        val order = aOrder().build()
        order.disable()
        assertFalse(order.isTransactable())
    }

    @Test
    fun `should return false when order is not finished`() {
        val order = aOrder().build()
        assertFalse(order.isFinished())
    }

    @Test
    fun `when closing an order, its status becomes finished`() {
        val order = aOrder().build()
        order.close()
        assertTrue(order.isFinished())
    }

    @Test
    fun `a closed order is not transactable`() {
        val order = aOrder().build()
        order.close()
        assertFalse(order.isTransactable())
    }

    @Test
    fun `should return false when order is reset after being finished`() {
        val order = aOrder().build()
        order.close()
        order.reset()
        assertFalse(order.isFinished())
    }

}