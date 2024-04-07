package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CryptoExchangeTest {

    lateinit var coin : Cryptocurrency
    lateinit var creationTime : LocalDateTime


    @BeforeEach
    fun setUp() {
        creationTime = LocalDateTime.now()
        coin = Cryptocurrency("BTCUSDT",68064.7, creationTime)
    }


    @Test
    fun testCryptocurrencyModel(){
        Assertions.assertEquals("BTCUSDT",coin.code)
        Assertions.assertEquals(68064.7,coin.price)
        Assertions.assertEquals(creationTime, coin.dateTime)
        Assertions.assertTrue(coin.priceHistory.isEmpty())
    }

    @Test
    fun testAddCriptyocurrencyPriceHistoryByPriceUpdate(){
        var updateTime = LocalDateTime.now()
        coin.updatePrice(68064.5,updateTime)

        Assertions.assertEquals(68064.5,coin.price)
        Assertions.assertEquals(updateTime, coin.dateTime)
        Assertions.assertEquals(coin.priceHistory.size,1)

        var priceHistory = coin.priceHistory
        Assertions.assertEquals(68064.7, priceHistory.first().price)
        Assertions.assertEquals(creationTime, priceHistory.first().dateTime)

    }
}