package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crypto")
@Tag(name = "Crypto", description = "Endpoints for crypto information")
class CryptoCurrencyController {

    @Autowired
    private lateinit var cryptoService: ICryptoService

    @Operation(summary = "Get all registered users")
    @GetMapping("/prices")
    fun getPrices(): ResponseEntity<Any> {
        return try {
            val prices = cryptoService.getPrices()
            ResponseEntity.status(HttpStatus.OK).body(prices)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/last24hsQuotes/{symbol}")
    fun getLast24hsQuotes(@PathVariable symbol: CryptoSymbol): ResponseEntity<Any> {
        return try {
            val lastQuotes = cryptoService.getLast24hsQuotes(symbol)
            ResponseEntity.status(HttpStatus.OK).body(lastQuotes)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }

}