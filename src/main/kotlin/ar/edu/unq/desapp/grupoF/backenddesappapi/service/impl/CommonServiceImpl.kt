package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommonServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : ICommonService {

    override fun getUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { Exception("User with id $id not found") }
    }

    override fun getTransaction(id: Long): Transaction {
        return transactionRepository.findById(id).orElseThrow { Exception("Transaction with id $id not found") }
    }

    override fun getCrypto(symbol: CryptoSymbol): Cryptocurrency {
        return cryptocurrencyRepository.findByName(symbol)
            ?: throw Exception("Cryptocurrency with symbol $symbol not found")
    }

    override fun getTransactionBy(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        return transactionRepository.findCompletedTransactionsByUserAndBetweenDates(userId, startDate, endDate)
    }

}
