package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "price_history")
class PriceHistory {

    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    var cryptocurrency: Cryptocurrency? = null

    var price: Double = 0.00

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var priceTime: LocalDateTime = LocalDateTime.now()
}