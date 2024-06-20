package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO

interface ICryptoService {
    fun getPrices(): List<CryptocurrencyPriceDTO>
    fun getCrypto(symbol: CryptoSymbol): Cryptocurrency
    fun getLast24hsQuotes(symbol: CryptoSymbol): Any?
}