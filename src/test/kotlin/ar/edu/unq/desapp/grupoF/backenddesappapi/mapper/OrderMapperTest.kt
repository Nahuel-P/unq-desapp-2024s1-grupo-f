package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class OrderMapperTest {

    @Test
    fun `toModel converts DTO to Order`() {
        val dto = mock(OrderRequestDTO::class.java)
        val user = mock(User::class.java)
        val crypto = mock(Cryptocurrency::class.java)
        val intentionType = IntentionType.BUY
        val ars = 100.0

        `when`(dto.amount).thenReturn(1.0)
        `when`(dto.price).thenReturn(50000.0)

        val order = OrderMapper.toModel(dto, user, crypto, intentionType, ars)

        assertEquals(user, order.ownerUser)
        assertEquals(crypto, order.cryptocurrency)
        assertEquals(1.0, order.amount)
        assertEquals(50000.0, order.price)
        assertEquals(intentionType, order.type)
        assertEquals(ars, order.priceARS)
    }

    @Test
    fun `toDTO converts Order to DTO`() {
        val order = mock(Order::class.java)
        val user = mock(User::class.java)
        val crypto = mock(Cryptocurrency::class.java)
        val cryptoName = CryptoSymbol.BTCUSDT
        val amount = 1.0
        val price = 50000.0
        val intentionType = IntentionType.BUY
        val ars = 100.0

        `when`(order.id).thenReturn(1L)
        `when`(order.cryptocurrency).thenReturn(crypto)
        `when`(order.amount).thenReturn(amount)
        `when`(order.price).thenReturn(price)
        `when`(order.type).thenReturn(intentionType)
        `when`(order.priceARS).thenReturn(ars)
        `when`(crypto.name).thenReturn(cryptoName)

        `when`(user.id).thenReturn(1L)
        `when`(user.firstName).thenReturn("Michael")
        `when`(user.lastName).thenReturn("Scott")
        `when`(order.ownerUser).thenReturn(user)
        val dto = OrderMapper.toCreateDTO(order)

        assertEquals(user.id, dto.ownerId)
        assertEquals(crypto.name, dto.cryptocurrency)
        assertEquals(amount, dto.amount)
        assertEquals(price, dto.price)
        assertEquals(intentionType, dto.type)
        assertEquals(ars, dto.arsPrice)
        assertEquals("Michael Scott", dto.ownerName)
    }
}