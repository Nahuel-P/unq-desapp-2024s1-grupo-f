package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.PriceHistoryBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.PriceHistoryRepository
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

    @Autowired
    private lateinit var priceHistoryRepository: PriceHistoryRepository

    private val binanceClient = BinanceClient()
    private val logger : Logger = LogManager.getLogger(CryptoServiceImpl::class.java)

    override fun getQuotes(): List<Cryptocurrency> {
        val cryptocurrencies = cryptocurrencyRepository.findAll()
        val symbols = cryptocurrencies.map { it.name!! }.toMutableList()
        val prices = binanceClient.getAllCryptoCurrencyPrices(symbols)
        prices.forEach { priceDTO ->
            val crypto = cryptocurrencies.find { it.name == priceDTO.symbol }
            crypto?.price = priceDTO.price!!
        }
        return cryptocurrencies
    }

    override fun getLast24hsQuotes(symbol: CryptoSymbol): List<PriceHistory> {
        val cryptocurrency = cryptocurrencyRepository.findByName(symbol)
            ?: throw IllegalArgumentException("Cryptocurrency with symbol $symbol not found")

        return cryptocurrency.getLast24hsQuotes()
    }

    @Scheduled(fixedRate = 600000)
//    @Scheduled(fixedRate = 60000)
    override fun updateQuotes() {
        val quotes = getQuotes()
        val oldPrices = getSystemQuotes()
        cryptocurrencyRepository.saveAll(quotes)
        priceHistoryRepository.saveAll(oldPrices)
    }

    private fun getSystemQuotes(): MutableList<PriceHistory> {
        val prices = mutableListOf<PriceHistory>()
        val cryptos = cryptocurrencyRepository.findAll()
        cryptos.forEach { crypto ->
            prices.add(PriceHistoryBuilder().withPrice(crypto.price!!).withCryptocurrency(crypto).build())
        }
        return prices
    }


}