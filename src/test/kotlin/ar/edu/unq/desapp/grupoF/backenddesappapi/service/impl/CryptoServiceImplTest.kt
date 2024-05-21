package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.BinanceClient
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.CryptocurrencyPriceDTO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "dev")
class CryptoServiceImplTest {

    @Autowired
    private lateinit var service: CryptoServiceImpl

    @MockBean
    private lateinit var repository: CryptocurrencyRepository

    @MockBean
    private lateinit var binanceClient: BinanceClient

    @Test
    fun `getPrices returns correct prices for all cryptocurrencies`() = runBlocking {
        val cryptocurrencies = listOf(
            Cryptocurrency().apply { name = CryptoSymbol.BTCUSDT },
            Cryptocurrency().apply { name = CryptoSymbol.ETHUSDT }
        )
        `when`(repository.findAll()).thenReturn(cryptocurrencies)
        `when`(binanceClient.getCryptoCurrencyPrice(CryptoSymbol.BTCUSDT)).thenReturn(CryptocurrencyPriceDTO("BTCUSDT", 70948.72))
        `when`(binanceClient.getCryptoCurrencyPrice(CryptoSymbol.ETHUSDT)).thenReturn(CryptocurrencyPriceDTO("ETHUSDT", 4000.0))

        val prices = service.getPrices()

        assertEquals(2, prices.size)
        assertEquals("BTCUSDT", prices[0].symbol)
        assertTrue(prices[0].price!! in 60000.0..90000.0)
        assertEquals("ETHUSDT", prices[1].symbol)
        assertTrue(prices[1].price!! in 3000.0..5000.0)
    }

    @Test
    fun `getCrypto returns correct cryptocurrency for given symbol`() {
        val cryptocurrency = Cryptocurrency().apply {
            name = CryptoSymbol.BTCUSDT
        }
        `when`(repository.findByName(CryptoSymbol.BTCUSDT)).thenReturn(cryptocurrency)

        val result = service.getCrypto("BTCUSDT")

        assertEquals(CryptoSymbol.BTCUSDT, result.name)
    }

}