package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus

@Service
class TransactionServiceImpl: ITransactionService {

    @Autowired
    private lateinit var transactionRepository: TransactionRepository
    @Autowired
    private lateinit var userService: IUserService
    @Autowired
    private lateinit var orderService: IOrderService

    override fun open(transaction: Transaction): Transaction {
////        recoverTrade(transaction)
//        transaction.order = orderService.getOrderById(transaction.order.id)
//        val userRequested = recoverUserRequested(transaction)
//        updateBuyerSeller(transaction, userRequested)
        transaction.counterParty = userService.getUser(transaction.counterParty?.id!!)
        transaction.order = orderService.getOrder(transaction.order?.id!!)
        return transactionRepository.save(transaction)
    }

    override fun paid(transaction: Transaction): Transaction {
        var transaction = this.getTransaction(transaction?.id!!)
        validatePaid(transaction)
        transaction.status = TransactionStatus.PAID
        return update(transaction)

    }

    override fun confirm(transaction: Transaction): Transaction {
        var transaction = this.getTransaction(transaction?.id!!)
        validateConfirm(transaction)
        transaction.status = TransactionStatus.CONFIRMED
        return update(transaction)
    }

    private fun getTransaction(id: Long): Transaction {
        return transactionRepository.findById(id).get()
    }

    private fun validatePaid(transaction: Transaction) {
        validateStatus(transaction, TransactionStatus.PENDING)
//        validateOrder(transaction)
//        validateUserRequested(transaction)
    }

    private fun validateConfirm(transaction: Transaction) {
        validateStatus(transaction, TransactionStatus.PAID)
    }

    private fun update(transaction: Transaction): Transaction {
        return transactionRepository.save(transaction)
    }

    private fun validateStatus(transaction: Transaction, status: TransactionStatus) {
        if (transaction.status != status) {
            throw Exception("Transaction status is not $status")
        }
    }

//    private fun validateOrder(transaction: Transaction) {
//        if (transaction.order == null) {
//            throw Exception("Transaction order is null")
//        }
//    }
//
//    private fun validateUserRequested(transaction: Transaction) {
//        if (transaction.counterParty == null) {
//            throw Exception("Transaction counterParty is null")
//        }
//    }

}

