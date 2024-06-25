package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity(name = "cryptocurrency")
class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The crypto name cannot be null.")
    var name: CryptoSymbol? = null

    var lastPrice: Double = 0.00

//    @JsonManagedReference
//    @OneToMany(mappedBy = "cryptocurrency", cascade = [CascadeType.ALL],fetch = FetchType.EAGER )
//    @OneToMany
//    @OneToMany(mappedBy = "cryptocurrency", cascade = [CascadeType.ALL],fetch = FetchType.EAGER )
//    var priceHistory: MutableList<PriceHistory> = mutableListOf()

    fun lastPrice(): Double? {
        return lastPrice
    }

//    fun getLast24hsQuotes(): List<PriceHistory> {
//        val priceHistories = filterPriceHistoriesInLast24Hours()
//        return groupPriceHistoriesByHourAndSort(priceHistories)
//    }

//    private fun filterPriceHistoriesInLast24Hours(): List<PriceHistory> {
//        val endDateTime = LocalDateTime.now()
//        val startDateTime = endDateTime.minusDays(1)
//        return this.priceHistory.filter {
//            it.priceTime.isAfter(startDateTime) && it.priceTime.isBefore(endDateTime)
//        }
//    }

//    private fun groupPriceHistoriesByHourAndSort(priceHistories: List<PriceHistory>): List<PriceHistory> {
//        return priceHistories.groupBy {
//            it.priceTime.truncatedTo(ChronoUnit.HOURS)
//        }.values.map { it.first() }.sortedByDescending { it.priceTime }
//    }
}