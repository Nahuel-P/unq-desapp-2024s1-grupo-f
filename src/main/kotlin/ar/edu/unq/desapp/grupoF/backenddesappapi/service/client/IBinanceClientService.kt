package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO

interface IBinanceClientService {
    fun getCryptoCurrencyPrice(symbol: CryptoSymbol): CryptocurrencyPriceDTO
    fun getAllCryptoCurrencyPrices(symbols: MutableList<CryptoSymbol>): Array<CryptocurrencyPriceDTO>
}