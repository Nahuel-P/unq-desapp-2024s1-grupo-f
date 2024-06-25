package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.BuyTransactionResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CancelTransactionResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.SellTransactionResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import java.time.LocalDateTime

class TransactionMapper {
    companion object {
        fun toModel(dto: TransactionCreateDTO, user: User, order: Order): Transaction {
            return TransactionBuilder().withOrder(order).withCounterParty(user).build()
        }

        fun toBuyResponseDTO(transaction: Transaction, message: String): BuyTransactionResponseDTO {
            return BuyTransactionResponseDTO(
                transaction.id!!,
                transaction.status!!,
                transaction.entryTime.toString(),
                transaction.endTime?.toString() ?: "",
                transaction.order!!.cryptocurrency!!.name!!,
                transaction.order!!.amount!!,
                transaction.order!!.price!!,
                transaction.order!!.priceARS!!,
                transaction.order!!.type!!,
                UserMapper.toBuyerDTO(transaction.buyer()!!),
                message
            )
        }

        fun toSellResponseDTO(transaction: Transaction, message: String): SellTransactionResponseDTO {
            return SellTransactionResponseDTO(
                transaction.id!!,
                transaction.status!!,
                transaction.entryTime.toString(),
                transaction.endTime?.toString() ?: "",
                transaction.order!!.cryptocurrency!!.name!!,
                transaction.order!!.amount!!,
                transaction.order!!.price!!,
                transaction.order!!.priceARS!!,
                transaction.order!!.type!!,
                UserMapper.toSellerDTO(transaction.seller()!!),
                message
            )
        }

        fun toCancelResponseDTO(transaction: Transaction, message: String): CancelTransactionResponseDTO {
            return CancelTransactionResponseDTO(
                transaction.id!!,
                transaction.status!!,
                transaction.entryTime.toString(),
                transaction.endTime?.toString() ?: "",
                transaction.order!!.cryptocurrency!!.name!!,
                transaction.order!!.amount!!,
                transaction.order!!.price!!,
                transaction.order!!.priceARS!!,
                transaction.order!!.type!!,
                message
            )
        }

    }
}