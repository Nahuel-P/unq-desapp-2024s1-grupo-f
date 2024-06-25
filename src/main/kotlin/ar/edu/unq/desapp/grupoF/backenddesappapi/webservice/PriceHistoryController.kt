package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.PriceHistoryMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IPriceHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crypto")
class PriceHistoryController {

    @Autowired
    private lateinit var priceHistoryService: IPriceHistoryService

    @GetMapping("/last24hsQuotes/{symbol}")
    fun getLast24hsQuotes(@PathVariable symbol: CryptoSymbol): ResponseEntity<Any> {
        return try {
            val lastQuotes = priceHistoryService.getLast24hsQuotes(symbol)
            val historyQuotesResponse = PriceHistoryMapper.toDTO(lastQuotes)
            ResponseEntity.status(HttpStatus.OK).body(historyQuotesResponse)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}