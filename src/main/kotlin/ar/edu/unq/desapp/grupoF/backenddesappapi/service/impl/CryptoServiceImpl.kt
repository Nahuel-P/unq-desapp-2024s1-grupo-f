package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.CryptocurrencyPrice
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CryptoServiceImpl : ICryptoService {
    private val binanceClient = BinanceClient()
    private val logger = LoggerFactory.getLogger(CryptoServiceImpl::class.java)

    override fun getPrices(): List<CryptocurrencyPrice> = runBlocking {
        CryptoSymbol.entries.map { symbol ->
            async {
                val price = binanceClient.getCryptoCurrencyPrice(symbol).price
                logger.info("Price for symbol $symbol: $price")
                CryptocurrencyPrice(symbol.toString(), price!!)
            }
        }.map { it.await() }
    }
}