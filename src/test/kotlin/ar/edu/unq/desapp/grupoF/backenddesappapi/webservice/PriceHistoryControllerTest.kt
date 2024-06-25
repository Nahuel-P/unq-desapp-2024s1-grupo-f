package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IPriceHistoryService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "michael", roles = ["USER"])
class PriceHistoryControllerTest {

    @MockBean
    private lateinit var priceHistoryService: IPriceHistoryService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `getLast24hsQuotes returns BAD_REQUEST status when quotes list is empty`() {
        val symbol = CryptoSymbol.BTCUSDT
        Mockito.`when`(priceHistoryService.getLast24hsQuotes(symbol)).thenReturn(listOf())

        mockMvc.perform(get("/crypto/last24hsQuotes/$symbol")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getLast24hsQuotes returns BAD_REQUEST status when service throws exception`() {
        val symbol = CryptoSymbol.BTCUSDT
        Mockito.`when`(priceHistoryService.getLast24hsQuotes(symbol)).thenThrow(RuntimeException("Error"))

        mockMvc.perform(get("/crypto/last24hsQuotes/$symbol")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }
}