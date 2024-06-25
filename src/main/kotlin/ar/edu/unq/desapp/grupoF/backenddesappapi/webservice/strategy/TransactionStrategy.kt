package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.strategy

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import org.springframework.http.ResponseEntity

interface TransactionStrategy {
    fun execute(transaction: Transaction, message: String): ResponseEntity<Any>
}

class BuyTransactionStrategy : TransactionStrategy {
    override fun execute(transaction: Transaction, message: String): ResponseEntity<Any> {
        val transactionResponse = TransactionMapper.toBuyResponseDTO(transaction,message)
        return ResponseEntity.ok().body(transactionResponse)
    }
}

class SellTransactionStrategy : TransactionStrategy {
    override fun execute(transaction: Transaction, message: String): ResponseEntity<Any> {
        val transactionResponse = TransactionMapper.toSellResponseDTO(transaction,message)
        return ResponseEntity.ok().body(transactionResponse)
    }
}