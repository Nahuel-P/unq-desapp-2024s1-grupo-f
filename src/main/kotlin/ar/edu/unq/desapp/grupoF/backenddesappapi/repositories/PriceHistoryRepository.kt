package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceHistoryRepository : JpaRepository<PriceHistory, Long> {
//    fun findByCryptocurrency(id: Long): MutableList<PriceHistory>
}