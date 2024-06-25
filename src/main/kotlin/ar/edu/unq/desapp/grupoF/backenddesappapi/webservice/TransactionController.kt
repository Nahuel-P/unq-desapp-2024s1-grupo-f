package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.models.media.Schema
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
    @Operation(summary = "Create a transaction", responses = [
        ApiResponse(responseCode = "200", description = "Transaction created", content = [Content(mediaType = "application/json",
            schema = io.swagger.v3.oas.annotations.media.Schema(implementation = TransactionResponseDTO::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
    @PostMapping("/create")
    fun create(@RequestBody transactionDTO: TransactionCreateDTO) : ResponseEntity<Any> {
        return try {
            val transaction = transactionService.create(transactionDTO)
            val transactionResponse = TransactionMapper.toResponseDTO(transaction,"Transaction created, waiting for payment by buyer")
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
    @Operation(summary = "Mark a transaction as paid", responses = [
        ApiResponse(responseCode = "200", description = "Transaction marked as paid",content = [Content(mediaType = "application/json",
            schema = io.swagger.v3.oas.annotations.media.Schema(implementation = TransactionResponseDTO::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
    @PutMapping("/paid")
    fun paid(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            val transaction = transactionService.paid(transactionDTO)
            val transactionResponse = TransactionMapper.toResponseDTO(transaction,"Transaction paid, waiting for confirmation by seller")
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
    @Operation(summary = "Confirm a transaction", responses = [
        ApiResponse(responseCode = "200", description = "Transaction confirmed", content = [Content(mediaType = "application/json",
            schema = io.swagger.v3.oas.annotations.media.Schema(implementation = TransactionResponseDTO::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
    @PutMapping("/confirm")
    fun confirm(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            val transaction = transactionService.confirm(transactionDTO)
            val transactionResponse = TransactionMapper.toResponseDTO(transaction,"Transaction confirmed by seller!")
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @Operation(summary = "Cancel a transaction", responses = [
    ApiResponse(responseCode = "200", description = "Transaction cancelled", content = [Content(mediaType = "application/json",
        schema = io.swagger.v3.oas.annotations.media.Schema(implementation = TransactionResponseDTO::class))]),
    ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
    @PutMapping("/cancel")
    fun cancel(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        return try {
            val transaction = transactionService.cancel(transactionDTO)
            val transactionResponse = TransactionMapper.toResponseDTO(transaction,"Transaction cancelled by user: "+transactionDTO.idUserRequest)
            ResponseEntity.ok().body(transactionResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

}


