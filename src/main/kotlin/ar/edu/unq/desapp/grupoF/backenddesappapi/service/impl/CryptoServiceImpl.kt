package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class CryptoServiceImpl : ICryptoService {
    @Autowired
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    private val binanceClient = BinanceClient()
    private val logger : Logger = LogManager.getLogger(CryptoServiceImpl::class.java)

    @Scheduled(fixedRate = 600000)
    override fun getQuotes(): List<CryptocurrencyPriceDTO> = runBlocking {
        val cryptocurrencies = cryptocurrencyRepository.findAll()
        val symbols = cryptocurrencies.map { it.name!! }.toMutableList()

        val prices = binanceClient.getAllCryptoCurrencyPrices(symbols)
        prices.map { priceDTO ->
            logger.info("Price for symbol ${priceDTO.symbol}: ${priceDTO.price}")
            CryptocurrencyPriceDTO(priceDTO.symbol, priceDTO.price)
        }
    }

    override fun getLast24hsQuotes(symbol: CryptoSymbol): List<PriceHistory> {
        val cryptocurrency = cryptocurrencyRepository.findByName(symbol)
            ?: throw IllegalArgumentException("Cryptocurrency with symbol $symbol not found")

        return cryptocurrency.getLast24hsQuotes()
    }
}