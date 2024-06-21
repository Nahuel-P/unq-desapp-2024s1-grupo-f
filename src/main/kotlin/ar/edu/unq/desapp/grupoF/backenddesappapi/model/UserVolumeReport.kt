package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import java.time.LocalDateTime
class UserVolumeReport{
    var totalUSD: Double = 0.0
    var totalARG: Double = 0.0
    var actives: MutableList<Active> = mutableListOf()
    val requestDate: LocalDateTime = LocalDateTime.now()
}
