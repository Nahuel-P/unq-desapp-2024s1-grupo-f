package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.utils.aTransaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserVolumeReportTest {

    private var aTranstaction = aTransaction().build()
    private var anotherTransaction = aTransaction().build()

    @Test
    fun `should add to volume report correctly`() {

        val userVolumeReport = UserVolumeReport()
        userVolumeReport.addToVolumeReport(aTranstaction)
        assertEquals(0.5 * 50000.0, userVolumeReport.totalUSD)
        assertEquals(50000.0 * 10 * 31, userVolumeReport.totalARG)
        assertTrue(userVolumeReport.actives.containsKey("BTCUSDT"))
        assertEquals(0.5, userVolumeReport.actives["BTCUSDT"]!!.cryptoNominalQuantity)
        assertEquals(50000.0, userVolumeReport.actives["BTCUSDT"]!!.usdPrice)
        assertEquals(50000.0 * 10 * 31, userVolumeReport.actives["BTCUSDT"]!!.argPrice)

    }

    @Test
    fun `should add to volume report correctly with multiple transactions`() {
        val userVolumeReport = UserVolumeReport()

        userVolumeReport.addToVolumeReport(aTranstaction)
        userVolumeReport.addToVolumeReport(anotherTransaction)

        assertEquals(0.5 * 50000.0 * 2, userVolumeReport.totalUSD)
        assertEquals(50000.0 * 10 * 31 * 2, userVolumeReport.totalARG)

        assertTrue(userVolumeReport.actives.containsKey("BTCUSDT"))
        assertEquals(0.5 * 2, userVolumeReport.actives["BTCUSDT"]!!.cryptoNominalQuantity)
        assertEquals(50000.0 * 2, userVolumeReport.actives["BTCUSDT"]!!.usdPrice)
        assertEquals(50000.0 * 10 * 31 * 2, userVolumeReport.actives["BTCUSDT"]!!.argPrice)
    }

}