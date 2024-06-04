package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "price_history")
class PriceHistory {

//    @ManyToOne
//    @JoinColumn(name = "cryptocurrency_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    var cryptocurrency: Cryptocurrency? = null

    var price: Double = 0.00



    var priceTime: LocalDateTime = LocalDateTime.now()
}