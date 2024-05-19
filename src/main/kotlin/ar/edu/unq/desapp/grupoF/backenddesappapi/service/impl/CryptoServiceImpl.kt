package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto.CryptocurrencyPriceDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CryptoServiceImpl(@Autowired private val cryptocurrencyRepository: CryptocurrencyRepository) : ICryptoService {
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

    override fun getCrypto(symbol: String): Cryptocurrency {
        val cryptoSymbol = CryptoSymbol.valueOf(symbol.uppercase(Locale.getDefault()))
        return cryptocurrencyRepository.findByName(cryptoSymbol)
            ?: throw Exception("Cryptocurrency with symbol $symbol not found")
    }
}