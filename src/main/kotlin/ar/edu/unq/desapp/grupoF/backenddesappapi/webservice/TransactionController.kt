package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.TransactionCreateDTO
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transactions", description = "Endpoints for transactions")
@Transactional
class TransactionController(private val transactionService: ITransactionService) {
    @PostMapping("/create")
    fun create(@RequestBody transactionDTO: TransactionCreateDTO) : ResponseEntity<Any> {
        val transac = toModel(transactionDTO)

        val newTran = transactionService.create(transac)
//        val transactionResponse = TransactionResponseDTO.fromModel(newTran)
//        return ResponseEntity.ok().body(transactionResponse)
        return ResponseEntity.ok().body(newTran)
    }
}

private fun toModel(transactionDTO: TransactionCreateDTO): Transaction {
    val order = OrderBuilder().build()
    order.id = transactionDTO.orderId

    val user = UserBuilder().build()
    user.id = transactionDTO.counterId

    val tran = TransactionBuilder()
        .withOrder(order)
        .withCounterParty(user)
        .build()

    return tran
}


