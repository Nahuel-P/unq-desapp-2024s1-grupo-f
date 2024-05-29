
import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl.TransactionServiceImpl
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [BackendDesappApiApplication::class])
class TransactionServiceImplTest {

    @Autowired
    private lateinit var service: TransactionServiceImpl

    @MockBean
    private lateinit var transactionRepository: TransactionRepository

    @MockBean
    private lateinit var userService: IUserService

    @MockBean
    private lateinit var orderService: IOrderService

    @Test
    fun `open should create and save a new transaction`() {
        val transactionDTO = mock(TransactionCreateDTO::class.java)
        val user = mock(User::class.java)
        val anotherUser = mock(User::class.java)
        val transaction = mock(Transaction::class.java)

        val order = OrderBuilder().withOwnerUser(user).withAmount(1.0).withPrice(1.0).withType(IntentionType.BUY).withPriceARS(1000.0).build()
        `when`(user.id).thenReturn(1L)
        `when`(anotherUser.id).thenReturn(2L)
        `when`(transactionDTO.idUserRequest).thenReturn(1L)
        `when`(transactionDTO.orderId).thenReturn(1L)
        `when`(userService.getUser(transactionDTO.idUserRequest)).thenReturn(anotherUser)
        `when`(orderService.getOrder(transactionDTO.orderId)).thenReturn(order)
        `when`(transactionRepository.save(any(Transaction::class.java))).thenReturn(transaction)

        assertNotEquals(order.ownerUser, anotherUser)

        val result = service.create(transactionDTO)

        assertEquals(transaction, result)
        verify(orderService).update(order)
    }

    @Test
    fun `open should throw an exception when order is not transactable`() {
        val transactionDTO = mock(TransactionCreateDTO::class.java)
        val user = mock(User::class.java)
        val order = mock(Order::class.java)

        `when`(userService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(orderService.getOrder(transactionDTO.orderId)).thenReturn(order)
        `when`(order.isTransactable()).thenReturn(false)

        assertThrows(Exception::class.java) {
            service.create(transactionDTO)
        }
    }

}