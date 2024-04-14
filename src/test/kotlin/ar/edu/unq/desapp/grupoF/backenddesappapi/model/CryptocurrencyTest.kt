
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class CryptocurrencyTest {

    fun aCryptocurrency(): CryptocurrencyBuilder {
        return CryptocurrencyBuilder()
            .withName(CryptoSymbol.BTCUSDT)
            .withCreated(LocalDateTime.now())
    }

    @Test
    fun `should create a cryptocurrency when it has valid data`() {
        assertDoesNotThrow { aCryptocurrency().build() }
    }

    @Test
    fun `throw exception for null name`() {
        assertThrows<IllegalArgumentException> {
            aCryptocurrency().withName(null).build()
        }
    }

}