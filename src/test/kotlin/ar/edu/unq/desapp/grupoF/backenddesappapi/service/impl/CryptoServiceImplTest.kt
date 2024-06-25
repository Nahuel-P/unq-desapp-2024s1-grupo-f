package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.client.MockBinanceClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class CryptoServiceImplTest {

    private var binanceClient = MockBinanceClientService()

    @MockBean
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    @Autowired
    private lateinit var cryptoService: CryptoServiceImpl

//    @Test
//    fun `updateQuotes updates and saves all cryptocurrency prices`() {
//        val cryptoPricesDTO = arrayOf(CryptocurrencyPriceDTO(CryptoSymbol.BTCUSDT, 50000.0))
//        val cryptocurrency = mock(Cryptocurrency::class.java)
//        val cryptocurrencies = listOf(cryptocurrency)
//        cryptoService.setBinanceClient(binanceClient)
//
//        `when`(binanceClient.getAllCryptoCurrencyPrices(anyList())).thenReturn(cryptoPricesDTO)
//        `when`(cryptocurrencyRepository.findAll()).thenReturn(cryptocurrencies)
//        `when`(cryptocurrency.lastPrice).thenReturn(50000.0)
//        `when`(cryptocurrency.name).thenReturn(CryptoSymbol.BTCUSDT)
//
//        cryptoService.updateQuotes()
//
//        assertEquals(50000.0, cryptocurrency.lastPrice)
//        verify(cryptocurrencyRepository).saveAll(cryptocurrencies)
//    }
}