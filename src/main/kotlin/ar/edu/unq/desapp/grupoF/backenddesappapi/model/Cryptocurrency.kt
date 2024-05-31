package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity(name = "cryptocurrency")
class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoSymbol? = null

    var price: Double = 0.00

//    @OneToMany(mappedBy = "cryptocurrency", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.EAGER)
    var priceHistory: MutableList<PriceHistory> = mutableListOf()

    fun lastPrice(): PriceHistory? {
        return priceHistory.maxByOrNull { it.priceTime }
    }

    fun pricesOver24hs(): List<PriceHistory> {
        return priceHistory.filter { it.priceTime.isAfter(LocalDateTime.now().minusDays(1)) }
    }
}