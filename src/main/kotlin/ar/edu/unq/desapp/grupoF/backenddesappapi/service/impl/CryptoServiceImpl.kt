package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto.CryptocurrencyPriceDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CryptoServiceImpl(private val cryptocurrencyRepository: CryptocurrencyRepository) : ICryptoService {
    private val binanceClient = BinanceClient()
    private val logger = LoggerFactory.getLogger(CryptoServiceImpl::class.java)

    override fun getPrices(): List<CryptocurrencyPriceDTO> = runBlocking {
        cryptocurrencyRepository.findAll().map { cryptocurrency ->
            async {
                val price = binanceClient.getCryptoCurrencyPrice(cryptocurrency.name!!).price
                logger.info("Price for symbol ${cryptocurrency.name}: $price")
                CryptocurrencyPriceDTO(cryptocurrency.name.toString(), price!!)
            }
        }.map { it.await() }
    }
}