package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Active
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport

class UserVolumeReportBuilder {
    private var totalUSD: Double = 0.0
    private var totalARG: Double = 0.0
    private var activeList: MutableList<Active> = mutableListOf()

    fun build(): UserVolumeReport {
        val userVolumeReport = UserVolumeReport()
        userVolumeReport.totalUSD = this.totalUSD
        userVolumeReport.totalARG = this.totalARG
        userVolumeReport.actives = this.activeList
        return userVolumeReport
    }

    fun withTotalUSD(totalUSD: Double): UserVolumeReportBuilder {
        this.totalUSD = totalUSD
        return this
    }

    fun withTotalARG(totalARG: Double): UserVolumeReportBuilder {
        this.totalARG = totalARG
        return this
    }

    fun withActiveList(activeList: List<Active>): UserVolumeReportBuilder {
        this.activeList = activeList.toMutableList()
        return this
    }


}