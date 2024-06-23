package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "price_history")
class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cryptocurrency_id")
    var cryptocurrency: Cryptocurrency? = null

    var price: Double = 0.00

    var priceTime: LocalDateTime = LocalDateTime.now()
}