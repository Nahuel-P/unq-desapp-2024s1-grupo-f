package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol

fun interface IPriceHistoryService {

    fun getLast24hsQuotes(symbol: CryptoSymbol): List<PriceHistory>
}