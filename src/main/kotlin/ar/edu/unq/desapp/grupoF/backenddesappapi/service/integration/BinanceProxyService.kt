package ar.edu.unq.desapp.grupoF.backenddesappapi.service.integration

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto.CryptocurrencyPriceDTO
import org.springframework.web.client.RestTemplate


class BinanceProxyService {

    private var restTemplate: RestTemplate = RestTemplate()
    private var baseURL: String = "https://api.binance.com/api/v3"

    fun getCryptoCurrencyPrice(symbol: CryptoSymbol): CryptocurrencyPriceDTO {
        val url = "$baseURL/ticker/price?symbol=$symbol"
        val response: CryptocurrencyPriceDTO = restTemplate.getForObject(url, CryptocurrencyPriceDTO::class.java)!!
        return CryptocurrencyPriceDTO(response.symbol, response.price)
    }



}