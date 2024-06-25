package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest

class OrderServiceImplTest {

    @MockBean
    private lateinit var orderRepository: OrderRepository

    @MockBean
    private lateinit var commonService: ICommonService

    @Autowired
    private lateinit var orderService: OrderServiceImpl

    @Test
    fun `createOrder creates a new order`() {
        val orderServiceImpl = Mockito.spy(orderService)

        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPrice(50000.0).build()
        val orderDTO = OrderRequestDTO(1L, CryptoSymbol.BTCUSDT, 1.0, 50000.0, IntentionType.BUY)
        val user = Mockito.mock(User::class.java)
        val order = OrderMapper.toModel(orderDTO, user, cryptocurrency, orderDTO.type, 50000.0)
        Mockito.`when`(user.id).thenReturn(1L)
        Mockito.`when`(commonService.getUser(orderDTO.userId)).thenReturn(user)
        Mockito.`when`(commonService.getCrypto(orderDTO.cryptocurrency)).thenReturn(cryptocurrency)
        Mockito.`when`(orderRepository.save(any(Order::class.java))).thenReturn(order)

        val result = orderServiceImpl.createOrder(orderDTO)

        assertNotNull(result)
        assertEquals(orderDTO.userId, result.ownerUser?.id)
        assertEquals(orderDTO.cryptocurrency, result.cryptocurrency?.name)
        assertEquals(orderDTO.amount, result.amount)
        assertEquals(orderDTO.price, result.price)
        assertEquals(orderDTO.type, result.type)
    }

    @Test
    fun `getActiveOrders returns all active orders`() {
        val order1 = OrderBuilder().build()
        val order2 = OrderBuilder().build()
        val orders = listOf(order1, order2)
        Mockito.`when`(orderRepository.findByIsActiveTrue()).thenReturn(orders)

        val result = orderService.getActiveOrders()

        assertEquals(orders, result)
    }

    @Test
    fun `getOrder returns order by id`() {
        val order = OrderBuilder().build()
        Mockito.`when`(orderRepository.findById(1L)).thenReturn(Optional.of(order))

        val result = orderService.getOrder(1L)

        assertEquals(order, result)
    }

    @Test
    fun `getOrder throws exception when order not found`() {
        Mockito.`when`(orderRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<Exception> { orderService.getOrder(1L) }
    }

    @Test
    fun `update updates order`() {
        val order = OrderBuilder().build()
        Mockito.`when`(orderRepository.save(order)).thenReturn(order)

        val result = orderService.update(order)

        assertEquals(order, result)
        verify(orderRepository).save(order)
    }

    @Test
    fun `createOrder throws exception when price is above margin`() {
        val orderDTO = OrderRequestDTO(1L, CryptoSymbol.BTCUSDT, 1.0, 10.5, IntentionType.BUY)
        val user = Mockito.mock(User::class.java)
        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPrice(10.0).build()

        Mockito.`when`(commonService.getUser(orderDTO.userId)).thenReturn(user)
        Mockito.`when`(commonService.getCrypto(orderDTO.cryptocurrency)).thenReturn(cryptocurrency)

        assertThrows<Exception> {
            orderService.createOrder(orderDTO)
        }
    }

    @Test
    fun `create order throws exception when price is below margin`() {
        val orderDTO = OrderRequestDTO(1L, CryptoSymbol.BTCUSDT, 1.0, 9.5, IntentionType.BUY)
        val user = Mockito.mock(User::class.java)
        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPrice(10.0).build()

        Mockito.`when`(commonService.getUser(orderDTO.userId)).thenReturn(user)
        Mockito.`when`(commonService.getCrypto(orderDTO.cryptocurrency)).thenReturn(cryptocurrency)

        assertThrows<Exception> {
            orderService.createOrder(orderDTO)
        }
    }

    @Test
    fun `getActiveOrders throws exception when no active orders exist`() {
        `when`(orderRepository.findByIsActiveTrue()).thenReturn(emptyList())

        val exception = assertThrows<Exception> {
            orderService.getActiveOrders()
        }

        assertEquals("There are no active orders", exception.message)
    }

}