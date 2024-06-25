package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency

interface ICryptoService {
    fun getQuotes(): List<Cryptocurrency>
    fun updateQuotes()
}