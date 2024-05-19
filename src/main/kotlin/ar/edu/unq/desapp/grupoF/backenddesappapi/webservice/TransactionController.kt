package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.TransactionMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transactions", description = "Endpoints for transactions")
@Transactional
class TransactionController() {

    @Autowired
    private lateinit var transactionService: ITransactionService
    @PostMapping("/create")
    fun create(@RequestBody transactionDTO: TransactionCreateDTO) : ResponseEntity<Any> {
        val transaction = TransactionMapper.fromCreateDto(transactionDTO)
        val newTran = transactionService.create(transaction)
//        val transactionResponse = TransactionResponseDTO.fromModel(newTran)
//        return ResponseEntity.ok().body(transactionResponse)
        return ResponseEntity.ok().body(newTran)
    }

//    @PutMapping("/transfer")
//    fun transfer(@RequestBody transactionDTO: TransactionRequestDTO) : ResponseEntity<Any>{
//        val transaction = TransactionMapper.fromRequestDto(transactionDTO)
////        val newTran = transactionService.transfer(transaction)
//    }

}


