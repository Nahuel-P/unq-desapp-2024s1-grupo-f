package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.OrderMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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

    @Operation(summary = "Create a new order", responses = [
        ApiResponse(responseCode = "200", description = "Order created", content = [Content(mediaType = "application/json",
            schema = Schema(implementation = OrderResponseDTO::class))]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])])
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

    @Operation(summary = "Get all active orders", responses = [
        ApiResponse(responseCode = "200", description = "List of all active orders", content = [
            Content(mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = OrderResponseDTO::class))
            )
        ]),
        ApiResponse(responseCode = "400", description = "Bad Request", content = [Content()])
    ])
    @GetMapping("/activeOrders")
    fun getActiveOrders(): ResponseEntity<Any> {
        return try {
            val orders = orderService.getActiveOrders()
            val orderResponse = OrderMapper.toDTO(orders)
            ResponseEntity.status(HttpStatus.OK).body(orderResponse)
        } catch (e: Exception) {
            logger.error("Error retrieving active orders", e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (e.message ?: "Unknown error")))
        }
    }
}