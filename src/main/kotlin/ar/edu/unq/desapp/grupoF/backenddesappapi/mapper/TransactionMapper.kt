package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionResponseDTO

class TransactionMapper {
    companion object {
        fun toModel(dto: TransactionCreateDTO, user: User, order: Order): Transaction {
            return TransactionBuilder().withOrder(order).withCounterParty(user).build()
        }

        fun toResponseDTO(transaction: Transaction): TransactionResponseDTO {
            return TransactionResponseDTO(
                UserMapper.userToDTO(transaction.counterParty!!),
                OrderMapper.toDTO(transaction.order!!),
                transaction.status!!,
                transaction.entryTime
            )
        }
    }
}