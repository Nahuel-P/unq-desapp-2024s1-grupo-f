package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
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
    @Operation (summary = "Create a new order")
    @PostMapping("/create")
    fun createOrder(@RequestBody orderDTO: OrderRequestDTO): ResponseEntity<Any> {
        return try {
            var order = orderService.createOrder(orderDTO)
            var orderResponse = OrderMapper.toCreateDTO(order)
            ResponseEntity.status(HttpStatus.OK).body(orderResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @Operation (summary = "Get all active orders")
    @GetMapping("/activeOrders")
    fun getActiveOrders(): ResponseEntity<Any> {
        return try {
            val activeOrders = orderService.getActiveOrders()
            val orderResponse = activeOrders.map { OrderMapper.toDTO(it) }
            ResponseEntity.status(HttpStatus.OK).body(orderResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}