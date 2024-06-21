package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    val logger = LoggerFactory.getLogger(OrderMapper::class.java)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var orderService: IOrderService

    @MockBean
    private lateinit var userService: IUserService

    @Test
    fun `createOrder returns BAD_REQUEST status when exception is thrown`() {
        val orderDTO = OrderRequestDTO(1, CryptoSymbol.BNBUSDT, 10.0, 2000.0, IntentionType.BUY)

        Mockito.`when`(orderService.createOrder(orderDTO)).thenThrow(RuntimeException("Test exception"))

        mockMvc.perform(post("/order/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(orderDTO)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getActiveOrders returns BAD_REQUEST status when exception is thrown`() {
        Mockito.`when`(orderService.getActiveOrders()).thenThrow(RuntimeException("Test exception"))

        mockMvc.perform(get("/order/activeOrders")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }


    @Test
    fun `createOrder returns OK status when order is created successfully`() {
        val userDTO = UserCreateDTO("Michael", "Scott", "prison.mike@gmail.com", "1725 Slough Avenue, Scranton", "Contraseña1!", "1234567890123456789012", "12345678")
        val user = UserMapper.toModel(userDTO)
        user.id = 1L
        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BNBUSDT).build()
        val orderDTO = OrderRequestDTO(user.id!!, CryptoSymbol.BNBUSDT, 10.0, 13000.0, IntentionType.BUY)
        val order = OrderMapper.toModel(orderDTO, user, cryptocurrency, IntentionType.BUY, 13000.0)
        order.id = 1L

        Mockito.`when`(userService.registerUser(userDTO)).thenReturn(user)
        Mockito.`when`(userService.getUser(user.id!!)).thenReturn(user)
        Mockito.`when`(orderService.createOrder(orderDTO)).thenReturn(order)

        mockMvc.perform(post("/order/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(orderDTO)))
            .andExpect(status().isOk)
    }

    @Test
    fun `getActiveOrders returns OK status when orders are retrieved successfully`() {
        val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BNBUSDT).build()
        val firstOrder = OrderBuilder().withCryptocurrency(cryptocurrency).withAmount(10.0).withPrice(2000.0).withType(IntentionType.BUY).build()
        firstOrder.id = 1L
        val secondOrder = OrderBuilder().withCryptocurrency(cryptocurrency).withAmount(20.0).withPrice(4000.0).withType(IntentionType.SELL).build()
        secondOrder.id = 2L
        val orders = listOf(firstOrder, secondOrder)

        Mockito.`when`(orderService.getActiveOrders()).thenReturn(orders)

        mockMvc.perform(get("/order/activeOrders")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

}