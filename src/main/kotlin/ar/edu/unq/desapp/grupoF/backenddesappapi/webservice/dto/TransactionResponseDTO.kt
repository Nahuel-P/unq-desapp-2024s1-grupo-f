package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

data class BuyTransactionResponseDTO(
    var transactionId : Long,

    var status: TransactionStatus,
    var entryTime: String,
    var endTime: String,

    var symbol: CryptoSymbol,
    var amount: Double,
    var price: Double,
    var arsPrice: Double,
    var type: IntentionType,

    var buyer: BuyerResponseDTO,
    var action : String
)

data class SellTransactionResponseDTO(
    var transactionId : Long,

    var status: TransactionStatus,
    var entryTime: String,
    var endTime: String,

    var symbol: CryptoSymbol,
    var amount: Double,
    var price: Double,
    var arsPrice: Double,
    var type: IntentionType,

    var seller: SellerResponseDTO,
    var action : String
)

data class CancelTransactionResponseDTO(
    var transactionId : Long,

    var status: TransactionStatus,
    var entryTime: String,
    var endTime: String,

    var symbol: CryptoSymbol,
    var amount: Double,
    var price: Double,
    var arsPrice: Double,
    var type: IntentionType,
    var action : String
)