package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "michael", roles = ["USER"])
@ExtendWith(SpringExtension::class)
class OrderControllerTest {

    @MockBean
    private lateinit var orderService: IOrderService

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val objectMapper: ObjectMapper = jacksonObjectMapper()


    @Test
    fun `createOrder returns OK status when order is created successfully`() {
        val orderDTO = OrderRequestDTO(1L, CryptoSymbol.BTCUSDT, 1.0, 50000.0, IntentionType.BUY)
        val user = UserBuilder().withFirstName("michael").withLastName("Scott").build()
        user.id = 1L
        val order = OrderBuilder()
                        .withOwnerUser(user)
                        .withCryptocurrency(CryptocurrencyBuilder()
                                            .withName(CryptoSymbol.BTCUSDT)
                                            .withPrice(50000.0).build())
                        .withPrice(5000.0)
                        .withType(IntentionType.BUY)
                        .withPriceARS(5000.0)
                        .withAmount(1.0)
                        .build()
        order.id = 1L

        `when`(orderService.createOrder(orderDTO)).thenReturn(order)

        mockMvc.perform(post("/order/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(orderDTO)))
            .andExpect(status().isOk)
    }

    @Test
    fun `createOrder returns BAD_REQUEST status when order creation fails`() {
        val orderDTO = OrderRequestDTO(1L, CryptoSymbol.BTCUSDT, 1.0, 50000.0, IntentionType.BUY)

        `when`(orderService.createOrder(orderDTO)).thenThrow(RuntimeException("Test exception"))

        mockMvc.perform(post("/order/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(orderDTO)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getActiveOrders returns OK status when orders are retrieved successfully`() {
        val user = mock(User::class.java)
        `when`(user.id).thenReturn(1L)
        `when`(user.firstName).thenReturn("michael")
        `when`(user.lastName).thenReturn("Scott")
        `when`(user.reputation()).thenReturn("5")

        val order = OrderBuilder()
            .withOwnerUser(user)
            .withCryptocurrency(CryptocurrencyBuilder()
                .withName(CryptoSymbol.BTCUSDT)
                .withPrice(50000.0).build())
            .withPrice(5000.0)
            .withType(IntentionType.BUY)
            .withPriceARS(5000.0)
            .withAmount(1.0)
            .build()
        order.id = 1L

        val orders = listOf(order)

        `when`(orderService.getActiveOrders()).thenReturn(orders)

        mockMvc.perform(get("/order/activeOrders")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `getActiveOrders returns BAD_REQUEST status when order retrieval fails`() {
        `when`(orderService.getActiveOrders()).thenThrow(RuntimeException("Test exception"))

        mockMvc.perform(get("/order/activeOrders")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

}