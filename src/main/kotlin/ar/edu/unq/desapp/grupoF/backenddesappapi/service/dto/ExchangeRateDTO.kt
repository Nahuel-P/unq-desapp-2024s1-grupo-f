package ar.edu.unq.desapp.grupoF.backenddesappapi.service.dto

data class ExchangeRateDTO (
    var moneda: String?,
    var casa: String?,
    var nombre: String?,
    var compra: Double?,
    var venta: Double?,
    var fechaActualizacion: String?
)
