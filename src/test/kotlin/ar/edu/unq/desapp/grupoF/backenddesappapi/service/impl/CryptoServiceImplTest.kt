package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.PriceHistoryRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class CryptoServiceImplTest {

    @MockBean
    private lateinit var binanceClient: BinanceClient

    @MockBean
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    @MockBean
    private lateinit var priceHistoryRepository: PriceHistoryRepository

    @Autowired
    private lateinit var cryptoService: CryptoServiceImpl

    @Test
    fun `updateQuotes updates and saves all cryptocurrency prices`() {
        val cryptoPricesDTO = arrayOf(CryptocurrencyPriceDTO(CryptoSymbol.BTCUSDT, 50000.0))
        val cryptocurrency = mock(Cryptocurrency::class.java)
        val cryptocurrencies = listOf(cryptocurrency)

        `when`(binanceClient.getAllCryptoCurrencyPrices(anyList())).thenReturn(cryptoPricesDTO)
        `when`(cryptocurrencyRepository.findAll()).thenReturn(cryptocurrencies)
        `when`(cryptocurrency.lastPrice).thenReturn(50000.0)
        `when`(cryptocurrency.name).thenReturn(CryptoSymbol.BTCUSDT)

        cryptoService.updateQuotes()

        assertEquals(50000.0, cryptocurrencies[0].lastPrice)
        verify(cryptocurrencyRepository).saveAll(cryptocurrencies)
    }
}