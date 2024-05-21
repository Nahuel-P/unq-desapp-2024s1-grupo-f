package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("integration")
class MockBinanceClientService : IBinanceClientService {

    override fun getCryptoCurrencyPrice(symbol: CryptoSymbol): CryptocurrencyPriceDTO {
        return CryptocurrencyPriceDTO("BTCUSDT", 50000.0)
    }

    override fun getAllCryptoCurrencyPrices(symbols: MutableList<CryptoSymbol>): Array<CryptocurrencyPriceDTO> {
        return arrayOf(CryptocurrencyPriceDTO("BTCUSDT", 50000.0), CryptocurrencyPriceDTO("ETHUSDT", 4000.0))
    }
}