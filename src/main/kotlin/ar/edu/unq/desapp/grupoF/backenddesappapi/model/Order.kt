package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import java.time.LocalDateTime

class Order(
    var ownerUser: UserExchange,
    var cryptocurrency: Cryptocurrency,
    var amount: Double,
    var price: Double,
    var type: IntentionType
    ) {

    var id: Int? = null
    var entryTime: LocalDateTime = LocalDateTime.now()
    var endTime: LocalDateTime? = null
    var isActive: Boolean = true

}