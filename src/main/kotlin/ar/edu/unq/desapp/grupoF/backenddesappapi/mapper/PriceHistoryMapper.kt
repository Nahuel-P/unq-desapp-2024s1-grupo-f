package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.HistoryResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.PriceHistoryResponseDTO

class PriceHistoryMapper {

    companion object {

        fun toDTO(prices: List<PriceHistory>): List<PriceHistoryResponseDTO> {
            return prices.map { priceHistory ->
                val history = prices.map { HistoryResponseDTO(it.price, it.priceTime.toString()) }
                PriceHistoryResponseDTO(priceHistory.cryptocurrency!!.name, history)
            }
        }
    }
}