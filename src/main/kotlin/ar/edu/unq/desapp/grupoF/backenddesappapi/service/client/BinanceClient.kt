package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
@Profile("dev")
class BinanceClient : IBinanceClientService {

    private var restTemplate: RestTemplate = RestTemplate()
    private var baseURL: String = "https://api.binance.com/api/v3"

    override fun getCryptoCurrencyPrice(symbol: CryptoSymbol): CryptocurrencyPriceDTO {
        val url = "$baseURL/ticker/price?symbol=$symbol"
        val response: CryptocurrencyPriceDTO = restTemplate.getForObject(url, CryptocurrencyPriceDTO::class.java)!!
        return CryptocurrencyPriceDTO(response.symbol, response.price)
    }

    override fun getAllCryptoCurrencyPrices(symbols: MutableList<CryptoSymbol>): Array<CryptocurrencyPriceDTO> {
        val symbolsListStr = symbols.joinToString(prefix = "[", postfix = "]", separator = ",") { "\"$it\"" }
        val url = "$baseURL/ticker/price?symbols=$symbolsListStr"
        return restTemplate.getForObject(url, Array<CryptocurrencyPriceDTO>::class.java)!!
    }
}