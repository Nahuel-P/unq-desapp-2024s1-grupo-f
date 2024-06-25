package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.CryptocurrencyMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crypto")
@Tag(name = "Crypto", description = "Endpoints for crypto information")
class CryptocurrencyController {

    @Autowired
    private lateinit var cryptoService: ICryptoService

    @Operation(summary = "Get all registered users")
    @Cacheable("quotes")
    @GetMapping("/quotes")
    fun getQuotes(): ResponseEntity<Any> {
        return try {
            val cryptos = cryptoService.getQuotes()
            val quotesResponse = CryptocurrencyMapper.toDTO(cryptos)
            ResponseEntity.status(HttpStatus.OK).body(quotesResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}