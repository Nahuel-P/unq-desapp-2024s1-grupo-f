package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserVolumeReportBuilder

class UserVolumeReportMapper {
    companion object {

        fun toModel(totalArs: Double, totalUsd: Double): UserVolumeReport {
            return UserVolumeReportBuilder().withTotalARG(totalArs).withTotalUSD(totalUsd).build()
        }
    }

}