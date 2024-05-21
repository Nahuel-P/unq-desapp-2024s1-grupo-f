package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class PriceHistory(
    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    var cryptocurrency: Cryptocurrency,
    var price: Double
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "symbol")
    var symbol: CryptoSymbol = cryptocurrency.name!!
    var priceTime: LocalDateTime = LocalDateTime.now()
}