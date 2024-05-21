package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CryptoServiceImpl : ICryptoService {
    @Autowired
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    private val binanceClient = BinanceClient()
    private val logger = LoggerFactory.getLogger(CryptoServiceImpl::class.java)

    override fun getPrices(): List<CryptocurrencyPriceDTO> = runBlocking {
        cryptocurrencyRepository.findAll().map { cryptocurrency ->
            async {
                val price = binanceClient.getCryptoCurrencyPrice(cryptocurrency.name!!).price
                logger.info("Price for symbol ${cryptocurrency.name}: $price")
                CryptocurrencyPriceDTO(cryptocurrency.name, price!!)
            }
        }.map { it.await() }
    }

    override fun getCrypto(symbol: CryptoSymbol): Cryptocurrency {
        return cryptocurrencyRepository.findByName(symbol)
            ?: throw Exception("Cryptocurrency with symbol $symbol not found")
    }
}