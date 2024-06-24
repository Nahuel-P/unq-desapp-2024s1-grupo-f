package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(SpringExtension::class)
class TransactionServiceImplTest {

    @Autowired
    private lateinit var service: TransactionServiceImpl

    @MockBean
    private lateinit var commonService: ICommonService

    @MockBean
    private lateinit var userService: IUserService

    @MockBean
    private lateinit var orderService: IOrderService

    @MockBean
    private lateinit var transactionRepository: TransactionRepository

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

        `when`(buyer.id).thenReturn(1L)
        `when`(seller.id).thenReturn(2L)
        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(buyer)
        `when`(transaction.buyer()).thenReturn(buyer)
        `when`(transaction.seller()).thenReturn(seller)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transaction.status).thenReturn(TransactionStatus.CONFIRMED)

        assertThrows(Exception::class.java) {
            service.cancel(transactionDTO)
        }
    }

    @Test
    fun `create should save a transaction when order is transactable and user is not the owner`() {
        val transactionDTO = mock(TransactionCreateDTO::class.java)
        val user = mock(User::class.java)
        val order = mock(Order::class.java)
        val ownerUser = mock(User::class.java)
        val cryptosymbol = mock(CryptoSymbol::class.java)
        val cryptocurrency = mock(Cryptocurrency::class.java)
        val savedTransaction = mock(Transaction::class.java)

        `when`(transactionDTO.idUserRequest).thenReturn(1L)
        `when`(ownerUser.id).thenReturn(2L)
        `when`(order.ownerUser).thenReturn(ownerUser)
        `when`(order.cryptocurrency).thenReturn(cryptocurrency)
        `when`(order.isTransferable()).thenReturn(true)
        `when`(order.isBuyOrder()).thenReturn(true)
        `when`(order.isAboveMarketPrice(cryptocurrency, order)).thenReturn(false)
        `when`(cryptocurrency.name).thenReturn(cryptosymbol)
        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getCrypto(cryptocurrency.name!!)).thenReturn(cryptocurrency)
        `when`(orderService.getOrder(transactionDTO.orderId)).thenReturn(order)
        `when`(transactionRepository.save(any(Transaction::class.java))).thenReturn(savedTransaction)

        service.create(transactionDTO)

        verify(transactionRepository, times(1)).save(any(Transaction::class.java))
    }
    @Test
    fun `paid should update transaction status to PAID when transaction status is PENDING`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val user = mock(User::class.java)

        val transaction = TransactionBuilder()
            .withOrder(Order())
            .withCounterParty(user)
            .withEntryTime(LocalDateTime.now())
            .build()

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }

        service.paid(transactionDTO)

        verify(transactionRepository, times(1)).save(transaction)
        assertEquals(TransactionStatus.PAID, transaction.status)
    }
    @Test
    fun `cancel should update transaction status to CANCELLED when transaction status is PENDING`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val user = mock(User::class.java)

        val transaction = TransactionBuilder()
            .withOrder(Order())
            .withCounterParty(user)
            .withEntryTime(LocalDateTime.now())
            .build()

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }

        service.cancel(transactionDTO)

        verify(transactionRepository, times(1)).save(transaction)
    }

    @Test
    fun `cancel should update transaction status to CANCELLED when transaction status is PAID`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val user = mock(User::class.java)

        val transaction = TransactionBuilder()
            .withOrder(Order())
            .withCounterParty(user)
            .withEntryTime(LocalDateTime.now())
            .build()

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }
        service.paid(transactionDTO)

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }

        service.cancel(transactionDTO)

        verify(transactionRepository, times(2)).save(transaction)
        assertEquals(TransactionStatus.CANCELLED_BY_USER, transaction.status)
    }


//    @Test
//    fun `confirm should update transaction status to CONFIRMED when transaction status is PAID`() {
//        val transactionDTO = mock(TransactionRequestDTO::class.java)
//        val user = mock(User::class.java)
//
//        val transaction = TransactionBuilder()
//            .withOrder(Order())
//            .withCounterParty(user)
//            .withEntryTime(LocalDateTime.now())
//            .build()
//
//        doReturn(user).`when`(user).increaseScore(20)
//        doReturn(user).`when`(user).increaseTransactions()
//        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
//        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
//        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }
//        service.paid(transactionDTO)
//
//        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
//        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)
//        `when`(transactionRepository.save(any(Transaction::class.java))).thenAnswer { it.arguments[0] }
//
//        service.confirm(transactionDTO)
//
//        verify(transactionRepository, times(2)).save(transaction)
//        assertEquals(TransactionStatus.CONFIRMED, transaction.status)
//    }

    @Test
    fun `confirm should throw exception when transaction status is not PAID`() {
        val transactionDTO = mock(TransactionRequestDTO::class.java)
        val user = mock(User::class.java)

        val transaction = TransactionBuilder()
            .withOrder(Order())
            .withCounterParty(user)
            .withEntryTime(LocalDateTime.now())
            .build()

        `when`(commonService.getUser(transactionDTO.idUserRequest)).thenReturn(user)
        `when`(commonService.getTransaction(transactionDTO.idTransaction)).thenReturn(transaction)

        val exception = assertThrows(Exception::class.java) {
            service.confirm(transactionDTO)
        }

        assertEquals("Transaction status is not PAID", exception.message)
    }
}