package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionRequestDTO
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
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
        try{
            val transaction = TransactionMapper.fromCreateDto(transactionDTO)
            val newTransaction = transactionService.open(transaction)
//        val transactionResponse = TransactionResponseDTO.fromModel(newTran)
//        return ResponseEntity.ok().body(transactionResponse)
            return ResponseEntity.ok().body(newTransaction)
        }catch (e: Exception){
            return ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }

    }




    @PutMapping("/paid")
    fun transfer(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        val transaction = TransactionMapper.fromRequestDto(transactionDTO)
        val paidTransaction = transactionService.paid(transaction)
        return ResponseEntity.ok().body(paidTransaction)
    }

    @PutMapping("/confirm")
    fun confirm(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
        val transaction = TransactionMapper.fromRequestDto(transactionDTO)
        val confirmedTransaction = transactionService.confirm(transaction)
        return ResponseEntity.ok().body(confirmedTransaction)
    }

}


