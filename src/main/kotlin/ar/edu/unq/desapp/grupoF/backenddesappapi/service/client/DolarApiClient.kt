package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto.ExchangeRateDTO
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

class DolarApiClient {

    private var restTemplate: RestTemplate = RestTemplate()
    private var baseURL: String = "https://dolarapi.com/v1"

    fun getRateUsdToArs(): ExchangeRateDTO {
        val url = "$baseURL/dolares/cripto"
        val response: ExchangeRateDTO = restTemplate.getForObject(url, ExchangeRateDTO::class.java)!!
        return response
    }
}