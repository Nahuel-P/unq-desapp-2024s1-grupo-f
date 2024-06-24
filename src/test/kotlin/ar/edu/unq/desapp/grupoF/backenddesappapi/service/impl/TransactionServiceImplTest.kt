package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
import ar.edu.unq.desapp.grupoF.backenddesappapi.BackendDesappApiApplication
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [BackendDesappApiApplication::class])
class TransactionServiceImplTest {

    @Autowired
    private lateinit var service: TransactionServiceImpl

    @MockBean
    private lateinit var commonService: ICommonService

    @MockBean
    private lateinit var userService: IUserService

    @MockBean
    private lateinit var orderService: IOrderService

    @Test
    fun `create should throw an exception when order is not transactable`() {
        val transactionDTO = mock(TransactionCreateDTO::class.java)
        val user = mock(User::class.java)
        val order = mock(Order::class.java)

        `when`(userService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(orderService.getOrder(transactionDTO.orderId)).thenReturn(order)
        `when`(order.isTransferable()).thenReturn(false)

        assertThrows(Exception::class.java) {
            service.create(transactionDTO)
        }
    }

    @Test
    fun `create should throw an exception when user is the owner of the order`() {
        val transactionDTO = mock(TransactionCreateDTO::class.java)
        val user = mock(User::class.java)
        val order = mock(Order::class.java)

        `when`(userService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(orderService.getOrder(transactionDTO.orderId)).thenReturn(order)
        `when`(order.isTransferable()).thenReturn(true)
        `when`(order.ownerUser).thenReturn(user)

        assertThrows(Exception::class.java) {
            service.create(transactionDTO)
        }
    }

    @Test
    fun `paid should throw an exception when transaction status is not PENDING`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val user = mock(User::class.java)
        val transaction = mock(Transaction::class.java)

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transaction.status).thenReturn(TransactionStatus.CONFIRMED)

        assertThrows(Exception::class.java) {
            service.paid(transactionDTO)
        }
    }

    @Test
    fun `confirm should throw an exception when transaction status is not PAID`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val user = mock(User::class.java)
        val transaction = mock(Transaction::class.java)

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transaction.status).thenReturn(TransactionStatus.PENDING)

        assertThrows(Exception::class.java) {
            service.confirm(transactionDTO)
        }
    }

    @Test
    fun `cancel should throw an exception when transaction status is CONFIRMED`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val buyer = mock(User::class.java)
        val seller = mock(User::class.java)
        val transaction = mock(Transaction::class.java)
        buyer.id = 1L
        seller.id = 2L

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(buyer)
        `when`(transaction.buyer()).thenReturn(buyer)
        `when`(transaction.seller()).thenReturn(seller)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transaction.status).thenReturn(TransactionStatus.CONFIRMED)

        assertThrows(Exception::class.java) {
            service.cancel(transactionDTO)
        }
    }

}