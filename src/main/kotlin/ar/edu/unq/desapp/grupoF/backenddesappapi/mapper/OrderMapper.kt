package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderResponseDTO

class OrderMapper {

    companion object {
        fun toModel(dto: OrderRequestDTO, user: User, crypto: Cryptocurrency, intentionType: IntentionType, ars: Double): Order {
            return OrderBuilder()
                .withOwnerUser(user)
                .withCryptocurrency(crypto)
                .withAmount(dto.amount)
                .withPrice(dto.price)
                .withType(intentionType)
                .withPriceARS(ars)
                .build()
        }
        fun toDTO(order: Order): OrderResponseDTO {
            return OrderResponseDTO(
                order.ownerUser?.id!!,
                order.cryptocurrency?.name!!,
                order.amount!!,
                order.price!!,
                order.type!!,
                order.priceARS!!
            )
        }
    }



}