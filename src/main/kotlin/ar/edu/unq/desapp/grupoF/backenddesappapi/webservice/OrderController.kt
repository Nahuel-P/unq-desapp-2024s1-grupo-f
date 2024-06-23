package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
@Tag(name = "Order", description = "Endpoints for order operations")
class OrderController {
    @Autowired
    private lateinit var orderService: IOrderService
    private val logger: Logger = LogManager.getLogger(OrderController::class.java)

    @Operation (summary = "Create a new order")
    @PostMapping("/create")
    fun createOrder(@RequestBody orderDTO: OrderRequestDTO): ResponseEntity<Any> {
        return try {
            val order = orderService.createOrder(orderDTO)
            val orderResponse = OrderMapper.toCreateDTO(order)
            ResponseEntity.status(HttpStatus.OK).body(orderResponse)
        } catch (e: Exception) {
            logger.error("Error creating order", e)
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
            logger.error("Error retrieving active orders", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (e.message ?: "Unknown error")))
        }
    }
}