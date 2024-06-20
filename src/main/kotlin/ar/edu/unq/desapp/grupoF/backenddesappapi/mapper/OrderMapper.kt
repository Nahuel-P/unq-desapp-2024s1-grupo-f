package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderCreateResponseDTO
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

        fun toCreateDTO(order: Order): OrderCreateResponseDTO {
            return OrderCreateResponseDTO(
                order.id!!,
                order.cryptocurrency?.name!!,
                order.amount!!,
                order.price!!,
                order.type!!,
                order.priceARS!!,
                order.ownerUser?.id!!,
                order.ownerUser?.firstName!! + " " + order.ownerUser?.lastName,
            )
        }

        fun toDTO(order: Order): OrderResponseDTO {
            return OrderResponseDTO(
                order.id!!,
                order.entryTime.toString(),
                order.cryptocurrency?.name!!,
                order.amount!!,
                order.price!!,
                order.type!!,
                order.priceARS!!,
                order.ownerUser?.id!!,
                order.ownerUser?.firstName!! + " " + order.ownerUser?.lastName,
                order.ownerUser?.reputation()!!
            )
        }
    }



}