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

    @Column(nullable = false)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoSymbol? = null
    @Column
    var createdAt: LocalDateTime? = null
    @OneToMany
    var priceHistory: MutableList<PriceHistory> = mutableListOf()

    fun lastPrice(): PriceHistory? {
        return priceHistory.maxByOrNull { it.priceTime }
    }

    fun pricesOver24hs(): List<PriceHistory> {
        return priceHistory.filter { it.priceTime.isAfter(LocalDateTime.now().minusDays(1)) }
    }

}

