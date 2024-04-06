package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

class Cryptocurrency() {

    private var code: String = ""
    private var price: Double  = 0.0
    private var dateTime: LocalDateTime? = null
    private var priceHistory: MutableList<PriceHistory> = mutableListOf()

    constructor(code: String, price: Double, deteTime: LocalDateTime ) : this() {
        this.code = code
        this.price = price
        this.dateTime = deteTime
    }

    fun code() = code
    fun price() = price
    fun dateTime() = dateTime
    fun priceHistory() = priceHistory

    fun updatePrice(newPrice: Double, updateTime: LocalDateTime) {
        var reccord = PriceHistory(this.price, this.dateTime)
        this.priceHistory.add(reccord)
        this.price = newPrice
        this.dateTime = updateTime

    }
}