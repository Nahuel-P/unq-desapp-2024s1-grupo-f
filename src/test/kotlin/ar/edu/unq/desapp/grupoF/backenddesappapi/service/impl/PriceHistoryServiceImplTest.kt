package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.PriceHistoryRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class PriceHistoryServiceImplTest {

    @MockBean
    private lateinit var priceHistoryRepository: PriceHistoryRepository

    @MockBean
    private lateinit var commonService: ICommonService

    @Autowired
    private lateinit var priceHistoryService: PriceHistoryServiceImpl

    @Test
    fun `getLast24hsQuotes returns correct price history when valid symbol is provided`() {
        val symbol = CryptoSymbol.BTCUSDT
        val mockCrypto = mock(Cryptocurrency::class.java)
        val mockPriceHistory = mock(PriceHistory::class.java)

        `when`(commonService.getCrypto(symbol)).thenReturn(mockCrypto)
        `when`(priceHistoryRepository.findByCryptocurrency(symbol)).thenReturn(listOf(mockPriceHistory))
        `when`(mockCrypto.getLast24hsQuotes(listOf(mockPriceHistory))).thenReturn(listOf(mockPriceHistory))

        val result = priceHistoryService.getLast24hsQuotes(symbol)

        assertEquals(listOf(mockPriceHistory), result)
    }

    @Test
    fun `getLast24hsQuotes returns empty list when no price history is found for symbol`() {
        val symbol = CryptoSymbol.BTCUSDT
        val mockCrypto = mock(Cryptocurrency::class.java)

        `when`(commonService.getCrypto(symbol)).thenReturn(mockCrypto)
        `when`(priceHistoryRepository.findByCryptocurrency(symbol)).thenReturn(emptyList())
        `when`(mockCrypto.getLast24hsQuotes(emptyList())).thenReturn(emptyList())

        val result = priceHistoryService.getLast24hsQuotes(symbol)

        assertEquals(emptyList<PriceHistory>(), result)
    }
}