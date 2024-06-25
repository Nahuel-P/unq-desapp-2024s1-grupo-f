package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.PriceHistoryBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.PriceHistoryRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.IBinanceClientService
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

    @Autowired
    private lateinit var binanceClient: IBinanceClientService

    private val logger : Logger = LogManager.getLogger(CryptoServiceImpl::class.java)

    override fun getQuotes(): List<Cryptocurrency> {
        return cryptocurrencyRepository.findAll()
    }


    @Scheduled(fixedRate = 60000)
    override fun updateQuotes() {
        val oldPrices = getSystemQuotes()

        val cryptocurrencies = getQuotes()
        val symbols = getSymbols()
        val cryptoPricesDTO = binanceClient.getAllCryptoCurrencyPrices(symbols)
        cryptoPricesDTO.forEach { priceDTO ->
            val crypto = cryptocurrencies.find { it.name == priceDTO.symbol }
            crypto?.lastPrice = priceDTO.price!!
        }
        cryptocurrencyRepository.saveAll(cryptocurrencies)
        priceHistoryRepository.saveAll(oldPrices)
    }

    private fun getSystemQuotes(): MutableList<PriceHistory> {
        val prices = mutableListOf<PriceHistory>()
        val cryptos = cryptocurrencyRepository.findAll()
        cryptos.forEach { crypto ->
            prices.add(PriceHistoryBuilder().withPrice(crypto.lastPrice).withCryptocurrency(crypto.name).build())
        }
        return prices
    }

    private fun getSymbols(): MutableList<CryptoSymbol> {
        return cryptocurrencyRepository.findAll().map { it.name!! }.toMutableList()
    }


}