package ar.edu.unq.desapp.grupoF.backenddesappapi.utils

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType

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

fun aBTCUSDT(): CryptocurrencyBuilder {
    return CryptocurrencyBuilder()
        .withName(CryptoSymbol.BTCUSDT).withPrice(50000.0)

}

fun aBuyOrder(): OrderBuilder {
    return OrderBuilder()
        .withOwnerUser(aUser().build())
        .withCryptocurrency(aBTCUSDT().build())
        .withAmount(0.5)
        .withPrice(50000.0)
        .withType(IntentionType.BUY)
        .withPriceARS(50000.0 * 10 * 31)

}

fun aTransaction(): TransactionBuilder {
    return TransactionBuilder()
        .withOrder(aBuyOrder().build())
        .withCounterParty(anotherUser().build())
}


