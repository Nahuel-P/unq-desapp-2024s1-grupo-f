package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.TransactionStatus

data class TransactionResponseDTO(
    var userRequest: UserResponseDTO,
    var order: OrderCreateResponseDTO,
    var status: TransactionStatus,
    var entryTime: String,
    var endTime: String,
    var destination: String,
    var message: String
)