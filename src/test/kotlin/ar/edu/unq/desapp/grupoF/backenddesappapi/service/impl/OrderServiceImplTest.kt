package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.DolarApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [BackendDesappApiApplication::class])
class OrderServiceImplTest {

    @MockBean
    private lateinit var orderRepository: OrderRepository

    @MockBean
    private lateinit var userService: IUserService

    @MockBean
    private lateinit var cryptoService: ICryptoService

    @MockBean
    private lateinit var cotizationService: DolarApiClient

    @Autowired
    private lateinit var service: OrderServiceImpl


//    @Test
//    fun `createOrder should return a new Order`() {
//        val orderDTO = mock(OrderRequestDTO::class.java)
//        val user = mock(User::class.java)
//        val cryptocurrency = mock(Cryptocurrency::class.java)
//        val order = Order()
//
//        `when`(orderDTO.type).thenReturn(IntentionType.BUY)
//        `when`(userService.getUser(orderDTO.userId)).thenReturn(user)
//        `when`(cryptoService.getCrypto(orderDTO.cryptocurrency)).thenReturn(cryptocurrency)
//        `when`(cotizationService.getRateUsdToArs()).thenReturn(ExchangeRateDTO(CryptoSymbol.BTCUSDT, "cripto", "Cripto", 1132.0, 1152.0, "2024-10-10"))
//        `when`(orderRepository.save(any(Order::class.java))).thenReturn(order)
//
//        val result = service.createOrder(orderDTO)
//
//        assertEquals(user, result.ownerUser)
//        assertEquals(cryptocurrency, result.cryptocurrency)
//        assertEquals(IntentionType.BUY, result.type)
//        assertTrue(result.priceARS!! in 1132.0..1172.0)
//    }
//
//    @Test
//    fun `getActiveOrders should return active orders`() {
//        val order = mock(Order::class.java)
//        `when`(orderRepository.findByIsActiveTrue()).thenReturn(listOf(order))
//
//        val result = service.getActiveOrders()
//
//        assertTrue(result.contains(order))
//    }
//
//    @Test
//    fun `getOrder should return the correct order`() {
//        val order = mock(Order::class.java)
//        `when`(orderRepository.findById(any(Long::class.java))).thenReturn(Optional.of(order))
//
//        val result = service.getOrder(1L)
//
//        assertEquals(order, result)
//    }
//
//    @Test
//    fun `update should return the updated order`() {
//        val order = mock(Order::class.java)
//        `when`(orderRepository.save(any(Order::class.java))).thenReturn(order)
//
//        val result = service.update(order)
//
//        assertEquals(order, result)
//    }
}