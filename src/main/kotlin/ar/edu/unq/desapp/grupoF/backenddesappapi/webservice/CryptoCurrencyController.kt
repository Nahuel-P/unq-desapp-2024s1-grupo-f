package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crypto")
@Tag(name = "Crypto", description = "Endpoints for crypto information")
class CryptoCurrencyController {

    private val binanceClient = BinanceClient()

    @GetMapping("/prices")
    fun getPrices(): Map<String, Double> = runBlocking {
        CryptoSymbol.entries.map { symbol ->
            async {
                symbol.toString() to binanceClient.getCryptoCurrencyPrice(symbol).price!!
            }
        }.associate { it.await() }
    }
}