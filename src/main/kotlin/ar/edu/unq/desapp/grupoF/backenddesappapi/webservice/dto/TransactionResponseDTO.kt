package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus
import java.time.LocalDateTime

data class TransactionResponseDTO(
    var userResponseDto: UserResponseDTO,
    var orderResponseDTO: OrderResponseDTO,
    var status: TransactionStatus,
    var entryTime: LocalDateTime
)