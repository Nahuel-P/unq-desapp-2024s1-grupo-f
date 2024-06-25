package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.*
import java.time.LocalDateTime

class TransactionMapper {
    companion object {
        fun toModel(dto: TransactionCreateDTO, user: User, order: Order): Transaction {
            return TransactionBuilder().withOrder(order).withCounterParty(user).build()
        }

        fun toResponseDTO(transaction: Transaction, message: String): TransactionResponseDTO {

            val destination = if (transaction.order!!.isBuyOrder()) {
                transaction.order!!.ownerUser!!.walletAddress!!
            } else {
                transaction.order!!.ownerUser!!.cvu!!
            }

            return TransactionResponseDTO(
                UserMapper.toDTO(transaction.counterParty!!),
                OrderMapper.toCreateDTO(transaction.order!!),
                transaction.status!!,
                transaction.entryTime.toString(),
                transaction.endTime?.toString() ?: "",
                destination,
                message
            )
        }

    }
}