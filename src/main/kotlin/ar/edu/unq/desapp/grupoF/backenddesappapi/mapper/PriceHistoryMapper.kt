package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.HistoryResponseDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.PriceHistoryResponseDTO

class PriceHistoryMapper {

    companion object {

        fun toDTO(prices: List<PriceHistory>): PriceHistoryResponseDTO {
            val symbol = prices.first().cryptocurrency
            val history = prices.map { HistoryResponseDTO(it.price, it.priceTime.toString()) }
            return PriceHistoryResponseDTO(symbol, history)
        }
    }
}