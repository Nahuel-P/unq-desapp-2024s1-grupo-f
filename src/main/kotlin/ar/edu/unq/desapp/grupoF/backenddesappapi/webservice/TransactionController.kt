package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.strategy.BuyTransactionStrategy
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.strategy.SellTransactionStrategy
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transactions", description = "Endpoints for transactions")
@Transactional
class TransactionController() {

    @Autowired
    private lateinit var transactionService: ITransactionService
    @PostMapping("/create")
    fun create(@RequestBody transactionDTO: TransactionCreateDTO) : ResponseEntity<Any> {
        return try {
            var transaction = transactionService.create(transactionDTO)
            if(transaction.order!!.isBuyOrder()){
                BuyTransactionStrategy().execute(transaction,"Transaction Created")
            } else {
                SellTransactionStrategy().execute(transaction,"Transaction Created")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/paid")
    fun paid(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            var transaction = transactionService.paid(transactionDTO)
            if(transaction.order!!.isBuyOrder()){
                BuyTransactionStrategy().execute(transaction,"Payment done")
            } else {
                SellTransactionStrategy().execute(transaction,"Payment done")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/confirm")
    fun confirm(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            var transaction = transactionService.confirm(transactionDTO)
            if(transaction.order!!.isBuyOrder()){
                BuyTransactionStrategy().execute(transaction,"Crypto Received")
            } else {
                SellTransactionStrategy().execute(transaction,"Crypto Received")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/cancel")
    fun cancel(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            var transaction = transactionService.cancel(transactionDTO)
            var transactionResponse = TransactionMapper.toCancelResponseDTO(transaction,"Transaction Cancelled by user id: "+transactionDTO.idUserRequest)
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

}


