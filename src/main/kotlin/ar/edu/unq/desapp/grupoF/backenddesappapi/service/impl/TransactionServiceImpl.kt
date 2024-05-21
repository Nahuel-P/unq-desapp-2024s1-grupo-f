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

    override fun open(transactionDTO: TransactionCreateDTO): Transaction {
        try {
            var user = userService.getUser(transactionDTO.idUserRequest)
            var order = orderService.getOrder(transactionDTO.orderId)
            validateStartTransaction(order,user)
            var transaction = TransactionMapper.toModel(transactionDTO, user, order)
            disableOrder(order)
            return transactionRepository.save(transaction)
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun getTransaction(id: Long): Transaction {
        return transactionRepository.findById(id).orElseThrow { Exception("Transaction with id $id not found") }
    }

    override fun paid(transactionDTO: TransactionRequestDTO): Transaction {
        try {
            var transaction = this.getTransaction(transactionDTO.idTransaction)
            var userRequest = userService.getUser(transactionDTO.idUserRequest)
            var order = orderService.getOrder(transactionDTO.idTransaction)
            validatePaid(transaction,userRequest,order)
            transaction.paid()
            return update(transaction)
        } catch ( e: Exception) {
            throw Exception("${e.message}")
        }
    }

    override fun confirm(transactionDTO: TransactionRequestDTO): Transaction {
        var transaction = this.getTransaction(transactionDTO.idTransaction)
        var user = userService.getUser(transactionDTO.idUserRequest)
        var order = orderService.getOrder(transactionDTO.idTransaction)
        validateConfirm(transaction,user)
        transaction.confirmed()
        order.close()
        updateReputacionTo(transaction, transaction.buyer()!!, transaction.seller()!!)
        return update(transaction)
    }

    private fun update(transaction: Transaction): Transaction {
        return transactionRepository.save(transaction)
    }

    private fun validateStartTransaction(order: Order, user: User) {
        validateTeransactable(order)
        validateRequestUser(order.ownerUser!!, user)
    }

    private fun validatePaid(transaction: Transaction, userRequest: User, order: Order) {
        validateStatus(transaction, TransactionStatus.PENDING)
        validateBuyerUser(transaction.buyer()!!, userRequest)
    }


    private fun validateConfirm(transaction: Transaction, userRequest: User) {
        validateStatus(transaction, TransactionStatus.PAID)
        validateUserAbleToConfirm(userRequest,transaction.seller()!!)
    }



    private fun validateStatus(transaction: Transaction, status: TransactionStatus) {
        if (transaction.status != status) {
            throw Exception("Transaction status is not $status")
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

    private fun validateBuyerUser(buyer: User, userRequest: User) {
        if (buyer.id != userRequest.id) {
            throw Exception("User ${userRequest.id} can't pay his own order")
        }
    }

    private fun validateUserAbleToConfirm( userRequest: User, sellerUser: User) {
        if (userRequest.id != sellerUser.id) {
            throw Exception("User ${userRequest.id} can't confirm because he is not the seller")
        }
    }
    private fun disableOrder(order: Order) {
        order.disable()
        orderService.update(order)
    }

    private fun updateReputacionTo(transaction: Transaction, buyer: User, seller: User) {
        val increment = calculateScoreBasedOnTimeLapse(transaction.entryTime, transaction.endTime)
        buyer.increaseScore(increment).increaseTransactions()
        seller.increaseScore(increment).increaseTransactions()
    }

    private fun calculateScoreBasedOnTimeLapse(entryTime: LocalDateTime, endTime: LocalDateTime?): Int {
        val duration = Duration.between(entryTime, endTime)
        return if (duration.toMinutes() > 30) {
            5
        } else {
            10
        }
    }


}

