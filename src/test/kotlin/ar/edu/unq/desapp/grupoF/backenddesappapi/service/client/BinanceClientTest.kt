package ar.edu.unq.desapp.grupoF.backenddesappapi.service.client
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIf
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration

class ActiveProfileResolver : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val profiles = System.getenv("SPRING_PROFILES_ACTIVE") ?: "dev"
        val map: Map<String, Any> = mapOf("spring.profiles.active" to profiles)
        val propertySource = MapPropertySource("myPropertySource", map)
        applicationContext.environment.propertySources.addFirst(propertySource)
    }
}

@SpringBootTest
@ContextConfiguration(initializers = [ActiveProfileResolver::class])
@EnabledIf("environment['SPRING_PROFILES_ACTIVE'] != 'integration'")
class BinanceClientTest {

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided`() {
        val binanceClient = BinanceClient()
        val symbol = CryptoSymbol.BTCUSDT

        val result = binanceClient.getCryptoCurrencyPrice(symbol)

        val lowerBound = 50000.0
        val upperBound = 70000.0

        assertTrue(result.price!! in lowerBound..upperBound)
    }

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided2`() {
        val dolarApiClient = DolarApiClient()
        val result = dolarApiClient.getRateUsdToArs().compra

        val lowerBound = 900.0
        val upperBound = 1200.0

        assertTrue(result!! in lowerBound..upperBound)
    }

    @Test
    fun `returns correct cryptocurrency price when valid symbol is provided22`() {
        val binanceClient = BinanceClient()
        val symbols = mutableListOf(CryptoSymbol.BTCUSDT, CryptoSymbol.ETHUSDT)

        val results = binanceClient.getAllCryptoCurrencyPrices(symbols)

        val lowerBoundBTC = 50000.0
        val upperBoundBTC = 70000.0

        val lowerBoundETH = 2000.0
        val upperBoundETH = 4000.0

        assertTrue(results.find { it.symbol == CryptoSymbol.BTCUSDT.toString() }?.price!! in (lowerBoundBTC..upperBoundBTC))
        assertTrue(results.find { it.symbol == CryptoSymbol.ETHUSDT.toString() }?.price!! in lowerBoundETH..upperBoundETH)
    }
}