package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto.CryptocurrencyPriceDTO

interface ICryptoService {
    fun getPrices(): List<CryptocurrencyPriceDTO>
    fun getCrypto(symbol: String): Cryptocurrency
}