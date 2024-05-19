package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICryptoService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.OrderRequestDTO
import java.util.*

class OrderMapper {

    companion object {
        fun fromCreateDto(userService: IUserService, cryptoService: ICryptoService, dto: OrderRequestDTO): Order {
            val user = userService.getUser(dto.userId)
            val intentionType = IntentionType.valueOf(dto.type.uppercase(Locale.getDefault()))

            val crypto = cryptoService.getCrypto(dto.cryptocurrency)
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