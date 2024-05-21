package ar.edu.unq.desapp.grupoF.backenddesappapi.utils

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import java.time.LocalDateTime

fun aUser(): UserBuilder {
    return UserBuilder()
        .withFirstName("Miguel Angel")
        .withLastName("Borja")
        .withEmail("el.colibri09@gmail.com")
        .withAddress("Av. Siempre Viva 742")
        .withPassword("Contraseña1!")
        .withCvu("1234567890123456789012")
        .withWalletAddress("12345678")
}

fun anotherUser(): UserBuilder {
    return UserBuilder().withEmail("test@gmail.com")
}

fun aCryptocurrency(): CryptocurrencyBuilder {
    val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
    val priceHistory = PriceHistory(cryptocurrency, 2.0)
    return CryptocurrencyBuilder()
        .withName(CryptoSymbol.BTCUSDT)
        .withCreated(LocalDateTime.now())
        .withPriceHistory(mutableListOf(priceHistory))
}

fun aOrder(): OrderBuilder {
    return OrderBuilder()
        .withOwnerUser(aUser().build())
        .withCryptocurrency(aCryptocurrency().build())
        .withAmount(10.0)
        .withPrice(50000.0)
        .withType(IntentionType.BUY)
}

fun aCryptocurrencySet(): MutableSet<Cryptocurrency> {
    return mutableSetOf(btcusdt())
}

fun btcusdt(): Cryptocurrency {
    return CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).withPriceHistory(btcusdtHistory()).build()
}

fun btcusdtHistory(): MutableList<PriceHistory> {
    val cryptocurrency = CryptocurrencyBuilder().withName(CryptoSymbol.BTCUSDT).build()
    val priceHistory = PriceHistory(cryptocurrency, 50000.0)
    return mutableListOf(priceHistory)
}