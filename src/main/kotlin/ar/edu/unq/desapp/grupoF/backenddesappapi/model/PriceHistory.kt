package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class PriceHistory(symbol: CryptoSymbol, price: Double) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var cryptocurrency: CryptoSymbol? = symbol
    var price: Double? = price
    var priceTime: LocalDateTime = LocalDateTime.now()
}