package ar.edu.unq.desapp.grupoF.backenddesappapi.model
import java.time.LocalDateTime

data class Cryptocurrency(
    var code: String = "",
    var price: Double = 0.0,
    var dateTime: LocalDateTime? = null,
    var priceHistory: MutableList<PriceHistory> = mutableListOf()
) {
    fun updatePrice(newPrice: Double, updateTime: LocalDateTime) {
        priceHistory.add(PriceHistory(price, dateTime))
        price = newPrice
        dateTime = updateTime
    }
}