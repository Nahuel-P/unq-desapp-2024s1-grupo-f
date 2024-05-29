package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import java.time.Duration
import java.time.LocalDateTime

@Service
class TransactionServiceImpl: ITransactionService {

    @Autowired
    private lateinit var transactionRepository: TransactionRepository
    @Autowired
    private lateinit var userService: IUserService
    @Autowired
    private lateinit var orderService: IOrderService

    override fun create(transactionDTO: TransactionCreateDTO): Transaction {
        try {
            val userRequest = userService.getUser(transactionDTO.idUserRequest)
            val order = orderService.getOrder(transactionDTO.orderId)
            validateStartTransaction(order,userRequest)
            disableOrder(order)
            val transaction = TransactionMapper.toModel(transactionDTO, userRequest, order)
            return transactionRepository.save(transaction)
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }
    }
    
    override fun paid(transactionDTO: TransactionRequestDTO): Transaction {
        try {
            val transaction = this.getTransaction(transactionDTO.idTransaction)
            val userRequest = userService.getUser(transactionDTO.idUserRequest)
            validatePaid(transaction,userRequest)
            transaction.paid()
            return update(transaction)
        } catch ( e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun confirm(transactionDTO: TransactionRequestDTO): Transaction {
        try {
            val transaction = this.getTransaction(transactionDTO.idTransaction)
            val userRequest = userService.getUser(transactionDTO.idUserRequest)
            validateConfirm(transaction,userRequest)
            transaction.confirmed()
            increseReputacionTo(transaction)
            return update(transaction)
        } catch ( e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun cancel(transactionDTO: TransactionRequestDTO): Transaction {
        try {
            val transaction = this.getTransaction(transactionDTO.idTransaction)
            val userRequest = userService.getUser(transactionDTO.idUserRequest)
            validateCancel(transaction,userRequest)
            transaction.cancelByUser()
            decreseReputacionTo(userRequest)
            return update(transaction)
        } catch ( e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun getTransaction(id: Long): Transaction {
        return transactionRepository.findById(id).orElseThrow { Exception("Transaction with id $id not found") }
    }

    private fun update(transaction: Transaction): Transaction {
        return transactionRepository.save(transaction)
    }

    private fun validateStartTransaction(order: Order, user: User) {
        validateTeransactable(order)
        validateRequestUser(order.ownerUser!!, user)
    }

    private fun validatePaid(transaction: Transaction, userRequest: User) {
        validateStatus(transaction, TransactionStatus.PENDING)
        validateUserAbleToPaid(transaction.buyer()!!, userRequest)
    }


    private fun validateConfirm(transaction: Transaction, userRequest: User) {
        validateStatus(transaction, TransactionStatus.PAID)
        validateUserAbleToConfirm(userRequest,transaction.seller()!!)
    }

    private fun validateCancel(transaction: Transaction, userRequest: User) {
        isInProgress(transaction)
        validateUserAbleToCancel(userRequest,transaction.buyer()!!,transaction.seller()!!)
    }

    private fun validateStatus(transaction: Transaction, status: TransactionStatus) {
        if (transaction.status != status) {
            throw Exception("Transaction status is not $status")
        }
    }

    private fun isInProgress(transaction: Transaction) {
        if (transaction.status == TransactionStatus.CONFIRMED) {
            throw Exception("Transaction is already confirmed. Can't be canceled")
        }
    }

    private fun validateTeransactable(order: Order) {
        if (!order.isTransactable()) {
            throw Exception("Transaction status is not able to be transfer.")
        }
    }

    private fun validateRequestUser(ownedUser: User, userRequest: User) {
        if (ownedUser.id == userRequest.id) {
            throw Exception("Owner user can't open a transfer for hiself.")
        }
    }

    private fun validateUserAbleToPaid(buyer: User, userRequest: User) {
        if (buyer.id != userRequest.id) {
            throw Exception("User ${userRequest.id} can't pay his own order")
        }
    }

    private fun validateUserAbleToConfirm( userRequest: User, sellerUser: User) {
        if (userRequest.id != sellerUser.id) {
            throw Exception("User ${userRequest.id} can't confirm because he is not the seller")
        }
    }

    private fun validateUserAbleToCancel(userRequest: User, buyer: User, seller: User) {
        if (userRequest.id != buyer.id && userRequest.id != seller.id) {
            throw Exception("User ${userRequest.id} can't cancel because he is not the buyer or the seller")
        }

    }

    private fun disableOrder(order: Order) {
        order.disable()
    }

    private fun increseReputacionTo(transaction: Transaction) {
        val increment = transaction.scoreBasedOnTimeLapse()
        val buyer = transaction.buyer()!!
        val seller = transaction.seller()!!
        buyer.increaseScore(increment).increaseTransactions()
        seller.increaseScore(increment).increaseTransactions()
    }

    private fun decreseReputacionTo(userRequest: User) {
        userRequest.decreaseScore()
    }

}

