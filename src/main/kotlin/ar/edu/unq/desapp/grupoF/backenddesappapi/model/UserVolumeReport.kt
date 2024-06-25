package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime

class UserVolumeReport {
    var totalUSD: Double = 0.0
    var totalARG: Double = 0.0
    var actives: MutableMap<String, Active> = mutableMapOf()
    val requestDate: LocalDateTime = LocalDateTime.now()

    fun addToVolumeReport(transaction: Transaction) {
        totalUSD += transaction.usdPrice() * transaction.nominalAmount()
        totalARG += transaction.arsQuote()

        val activeName = transaction.order!!.cryptocurrency!!.name.toString()
        val active = actives.getOrDefault(activeName, Active())
        active.crypto = transaction.order!!.cryptocurrency!!.name
        active.cryptoNominalQuantity += transaction.nominalAmount()
        active.usdPrice += transaction.usdPrice()
        active.argPrice += transaction.arsQuote()

        actives[activeName] = active
    }
}
