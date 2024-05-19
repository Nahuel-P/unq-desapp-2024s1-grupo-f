package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO

class OrderMapper {

    companion object {
        fun fromCreateDto(dto: OrderRequestDTO, user: User, intentionType: IntentionType, crypto: Cryptocurrency): Order {
            return OrderBuilder()
                .withOwnerUser(user)
                .withCryptocurrency(crypto)
                .withAmount(dto.amount)
                .withPrice(dto.price)
                .withType(intentionType)
                .build()
        }
    }
}