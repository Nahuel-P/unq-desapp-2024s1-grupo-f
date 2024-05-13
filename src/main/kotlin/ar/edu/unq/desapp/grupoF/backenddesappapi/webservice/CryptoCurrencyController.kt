package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto.CryptocurrencyPriceDTO
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
    fun getPrices(): List<CryptocurrencyPriceDTO> = runBlocking {
        CryptoSymbol.entries.map { symbol ->
            async {
                CryptocurrencyPriceDTO(symbol.toString(), binanceClient.getCryptoCurrencyPrice(symbol).price!!)
            }
        }.map { it.await() }
    }
}