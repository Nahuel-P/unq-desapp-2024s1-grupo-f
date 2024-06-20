package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity(name = "cryptocurrency")
class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoSymbol? = null

    var price: Double = 0.00

//    @OneToMany(mappedBy = "cryptocurrency", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
//    @OneToMany(fetch = FetchType.EAGER)

    @JsonManagedReference
    @OneToMany(mappedBy = "cryptocurrency", cascade = [CascadeType.ALL],fetch = FetchType.EAGER )
    var priceHistory: MutableList<PriceHistory> = mutableListOf()

    fun lastPrice(): Double? {
        return price
    }

    fun pricesOver24hs(): List<PriceHistory> {
        return priceHistory.filter { it.priceTime.isAfter(LocalDateTime.now().minusDays(1)) }
    }

    fun isAboveMarginPrice(marginPercentage: Double, userPrice: Double): Boolean {
        val marginFactor = 1 + marginPercentage / 100
        val upperBound = price * marginFactor
        return userPrice > upperBound
    }

    fun isBelowMarginPrice(marginPercentage: Double, userPrice: Double): Boolean {
        val marginFactor = 1 - marginPercentage / 100
        val lowerBound = price * marginFactor
        return userPrice < lowerBound
    }
}