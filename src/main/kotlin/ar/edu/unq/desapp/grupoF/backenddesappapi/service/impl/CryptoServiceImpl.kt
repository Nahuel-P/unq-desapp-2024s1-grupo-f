package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class CryptoServiceImpl : ICryptoService {
    @Autowired
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    private val binanceClient = BinanceClient()
    private val logger = LoggerFactory.getLogger(CryptoServiceImpl::class.java)

    override fun getQuotes(): Array<CryptocurrencyPriceDTO> {
        val symbols = cryptocurrencyRepository.findAll().map { it.name!! }.toMutableList()
        return binanceClient.getAllCryptoCurrencyPrices(symbols)
    }

    override fun getCrypto(symbol: CryptoSymbol): Cryptocurrency {
        return cryptocurrencyRepository.findByName(symbol)
            ?: throw Exception("Cryptocurrency with symbol $symbol not found")
    }

    override fun getLast24hsQuotes(symbol: CryptoSymbol): Any? {
        val cryptocurrency = cryptocurrencyRepository.findByName(symbol)
            ?: throw IllegalArgumentException("Cryptocurrency with symbol $symbol not found")

        return cryptocurrency.getLast24hsQuotes()
    }
}