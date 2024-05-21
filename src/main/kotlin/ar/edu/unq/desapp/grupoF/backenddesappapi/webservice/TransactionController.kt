package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
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
    @PostMapping("/open")
    fun create(@RequestBody transactionDTO: TransactionCreateDTO) : ResponseEntity<Any> {
        return try {
            var transaction = transactionService.open(transactionDTO)
            var transactionResponse = TransactionMapper.toResponseDTO(transaction)
            ResponseEntity.status(HttpStatus.OK).body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/paid")
    fun paid(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            var transaction = transactionService.paid(transactionDTO)
            var transactionResponse = TransactionMapper.toResponseDTO(transaction)
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @PutMapping("/confirm")
    fun confirm(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            var transaction = transactionService.confirm(transactionDTO)
            var transactionResponse = TransactionMapper.toResponseDTO(transaction)
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

}


