package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val userService: IUserService,
    private val orderService: IOrderService) : ITransactionService {

    override fun create(transaction: Transaction): Transaction {
////        recoverTrade(transaction)
//        transaction.order = orderService.getOrderById(transaction.order.id)
//        val userRequested = recoverUserRequested(transaction)
//        updateBuyerSeller(transaction, userRequested)
        transaction.counterParty = userService.getUser(transaction.counterParty?.id!!)
        transaction.order = orderService.getOrder(transaction.order?.id!!)
        return transactionRepository.save(transaction)
//        return transaction
    }
}

