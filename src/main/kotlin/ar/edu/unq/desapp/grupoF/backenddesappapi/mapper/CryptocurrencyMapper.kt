package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO

class CryptocurrencyMapper {

    companion object {

        fun toDTO(cryptocurrency: Cryptocurrency): CryptocurrencyPriceDTO {
            return CryptocurrencyPriceDTO(cryptocurrency.name!!, cryptocurrency.price)
        }
    }
}