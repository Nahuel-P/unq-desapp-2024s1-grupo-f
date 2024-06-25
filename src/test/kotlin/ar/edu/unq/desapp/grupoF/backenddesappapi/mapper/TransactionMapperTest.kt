package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.BuyTransactionResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.BuyerResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime

class TransactionMapperTest {

    @Test
    fun `toModel should return a Transaction with the same order and counterParty as the input`() {
        val dto = Mockito.mock(TransactionCreateDTO::class.java)
        val user = Mockito.mock(User::class.java)
        val order = Mockito.mock(Order::class.java)

        val result = TransactionMapper.toModel(dto, user, order)

        assertEquals(user, result.counterParty)
        assertEquals(order, result.order)
    }
}