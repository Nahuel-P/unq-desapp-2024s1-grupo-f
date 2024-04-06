package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

class PriceHistory() {

    private var price: Double  = 0.0
    private var dateTime: LocalDateTime? = null

    constructor(price: Double, dateTime: LocalDateTime? ) : this() {
        this.price = price
        this.dateTime = dateTime
    }

    fun price() = price
    fun dateTime() = dateTime

}