package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "dev")
class CryptoCurrencyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var cryptoService: ICryptoService

    @Test
    fun `getPrices returns OK status`() {
        Mockito.`when`(cryptoService.getQuotes()).thenReturn(listOf())
        mockMvc.perform(get("/crypto/quotes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }
    @Test
    fun `getQuotes returns BAD_REQUEST status when service throws exception`() {
        Mockito.`when`(cryptoService.getQuotes()).thenThrow(RuntimeException("Error from Service"))
        mockMvc.perform(get("/crypto/quotes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

//    @Test
//    fun `getLast24hsQuotes returns OK status when service returns data`() {
//        Mockito.`when`(cryptoService.getLast24hsQuotes(CryptoSymbol.BTCUSDT)).thenReturn(listOf<PriceHistory>())
//        mockMvc.perform(get("/crypto/last24hsQuotes/BTCUSDT")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk)
//    }
//
//    @Test
//    fun `getLast24hsQuotes returns BAD_REQUEST status when service throws exception`() {
//        Mockito.`when`(cryptoService.getLast24hsQuotes(CryptoSymbol.BTCUSDT)).thenThrow(IllegalArgumentException("Error from Service"))
//        mockMvc.perform(get("/crypto/last24hsQuotes/BTCUSDT")
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest)
//    }

}