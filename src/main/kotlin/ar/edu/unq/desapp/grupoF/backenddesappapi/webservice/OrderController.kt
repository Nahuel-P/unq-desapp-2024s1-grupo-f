package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/order")
@Tag(name = "Order", description = "Endpoints for order operations")
class OrderController {
    @Autowired
    private lateinit var orderService: IOrderService
    @Autowired
    private lateinit var userService: IUserService
    @Autowired
    private lateinit var cryptoService: ICryptoService

    @Operation (summary = "Create a new order")
    @PostMapping("/createOrder")
    fun createOrder(@RequestBody dto: OrderRequestDTO): ResponseEntity<Any> {
        return try {
            val user = userService.getUser(dto.userId)
            val intentionType = IntentionType.valueOf(dto.type.uppercase(Locale.getDefault()))
            val crypto = cryptoService.getCrypto(dto.cryptocurrency)

            val order = OrderMapper.fromCreateDto(dto, user, intentionType, crypto)

            orderService.createOrder(order)
            ResponseEntity.status(HttpStatus.OK).body(order)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @Operation (summary = "Get all active orders")
    @GetMapping("/activeOrders")
    fun getActiveOrders(): ResponseEntity<Any> {
        return try {
            val orders = orderService.getActiveOrders()
            ResponseEntity.status(HttpStatus.OK).body(orders)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}