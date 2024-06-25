package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "michael", roles = ["USER"])
@ExtendWith(SpringExtension::class)
class CryptoCurrencyControllerTest {

    @MockBean
    private lateinit var cryptoService: ICryptoService

    @MockBean
    private lateinit var cryptocurrencyRepository: CryptocurrencyRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

//    @Test
//    fun `getQuotes returns OK status when service returns data`() {
//        val cryptocurrency1 = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPrice(50000.0).build()
//        val cryptocurrency2 = CryptocurrencyBuilder().withName(CryptoSymbol.ETHUSDT).withPrice(50000.0).build()
//
//        val cryptos = listOf(cryptocurrency1, cryptocurrency2)
//        `when`(cryptoService.getQuotes()).thenReturn(cryptos)
//        `when`(cryptocurrencyRepository.findAll()).thenReturn(cryptos)
//
//        mockMvc.perform(
//            get("/crypto/quotes")
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(status().isOk)
//    }

    @Test
    fun `getQuotes returns BAD_REQUEST status when service throws exception`() {
        Mockito.`when`(cryptoService.getQuotes()).thenThrow(RuntimeException("Error from Service"))
        mockMvc.perform(
            get("/crypto/quotes")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }
}