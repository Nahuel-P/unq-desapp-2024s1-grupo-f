package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.CryptocurrencyPrice

interface ICryptoService {
    fun getPrices(): List<CryptocurrencyPrice>
}