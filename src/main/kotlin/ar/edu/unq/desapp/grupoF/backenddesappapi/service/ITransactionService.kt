package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User

interface ITransactionService {
    fun open(transaction: Transaction): Transaction
    fun paid(transaction: Transaction): Transaction
    fun confirm(transaction: Transaction): Transaction

}