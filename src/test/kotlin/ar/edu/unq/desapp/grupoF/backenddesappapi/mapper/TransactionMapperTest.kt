import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class TransactionMapperTest {

    @Test
    fun `fromCreateDto converts DTO to Transaction`() {
        val dto = mock(TransactionCreateDTO::class.java)
        val order = mock(Order::class.java)
        val user = mock(User::class.java)

        `when`(dto.orderId).thenReturn(1L)
        `when`(dto.counterId).thenReturn(2L)
        `when`(order.id).thenReturn(1L)
        `when`(user.id).thenReturn(2L)

        val transaction = TransactionMapper.fromCreateDto(dto)

        assertEquals(order.id, transaction.order?.id!!)
        assertEquals(user.id, transaction.counterParty?.id!!)
    }

    @Test
    fun `fromRequestDto converts DTO to Transaction`() {
        val dto = mock(TransactionRequestDTO::class.java)
        val transaction = mock(Transaction::class.java)
        val user = mock(User::class.java)

        `when`(dto.idTransaction).thenReturn(1L)
        `when`(dto.idUserRequest).thenReturn(2L)
        `when`(transaction.id).thenReturn(1L)
        `when`(user.id).thenReturn(2L)

        val resultTransaction = TransactionMapper.fromRequestDto(dto)

        assertEquals(transaction.id, resultTransaction.id)
        assertEquals(user.id, resultTransaction.counterParty?.id!!)
    }
}