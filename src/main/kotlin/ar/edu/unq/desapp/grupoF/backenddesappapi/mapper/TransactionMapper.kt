package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO

class TransactionMapper {
    companion object {

        fun fromCreateDto(dto: TransactionCreateDTO): Transaction {
            val order = Order()
            order.id = dto.orderId
            val user = User()
            user.id = dto.counterId
            return TransactionBuilder().withOrder(order).withCounterParty(user).build()
        }

        fun fromRequestDto(dto: TransactionRequestDTO): Transaction {
            val transaction = Transaction()
            transaction.id = dto.idTransaction
            val user = User()
            user.id = dto.idUserRequest
            transaction.counterParty = user
            return transaction
        }
    }
}