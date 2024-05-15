package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
@Tag(name = "Order", description = "Endpoints for order operations")
class OrderController(
    private val orderService: IOrderService,
    private val userService: IUserService
) {

    @Operation (summary = "Create a new order")
    @PostMapping("/createOrder")
    fun createOrder(@RequestBody request: OrderRequestDTO): ResponseEntity<Any> {
        return try {
            val user = userService.findUser(request.userId)
            val order = orderService.createOrder(user, request.cryptocurrency, request.amount, request.price, request.type)
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