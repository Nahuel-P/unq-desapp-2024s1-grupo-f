package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import java.time.LocalDateTime

interface ICommonService {
    fun getUser(id: Long): User
    fun getTransaction(id: Long): Transaction
    fun getCrypto(symbol: CryptoSymbol): Cryptocurrency
    fun getTransactionBy(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction>
}