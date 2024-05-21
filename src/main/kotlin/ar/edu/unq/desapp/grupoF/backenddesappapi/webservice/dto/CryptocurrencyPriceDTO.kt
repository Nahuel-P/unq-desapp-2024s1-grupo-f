package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol

data class CryptocurrencyPriceDTO(
    val symbol: CryptoSymbol?,
    val price: Double?,
)