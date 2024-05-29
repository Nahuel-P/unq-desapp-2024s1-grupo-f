package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO

interface ITransactionService {
    fun create(transactionDTO: TransactionCreateDTO): Transaction
    fun paid(transactionDTO: TransactionRequestDTO): Transaction
    fun confirm(transactionDTO: TransactionRequestDTO): Transaction
    fun cancel(transactionDTO: TransactionRequestDTO): Transaction
    fun getTransaction(id: Long): Transaction

}