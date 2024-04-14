
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CryptoSymbolTest {

    @Test
    fun `CrytoSymbol values should match predefined list`() {
        val expectedSymbols = listOf(
            "ALICEUSDT",
            "MATICUSDT",
            "AXSUSDT",
            "AAVEUSDT",
            "ATOMUSDT",
            "NEOUSDT",
            "DOTUSDT",
            "ETHUSDT",
            "CAKEUSDT",
            "BTCUSDT",
            "BNBUSDT",
            "ADAUSDT",
            "TRXUSDT",
            "AUDIOUSDT"
        )

        val actualSymbols = CryptoSymbol.entries.map { it.name }

        Assertions.assertEquals(expectedSymbols, actualSymbols)
    }

    @Test
    fun `CrytoSymbol count should match predefined list count`() {
        val expectedCount = 14
        val actualCount = CryptoSymbol.entries.size

        Assertions.assertEquals(expectedCount, actualCount)
    }
}