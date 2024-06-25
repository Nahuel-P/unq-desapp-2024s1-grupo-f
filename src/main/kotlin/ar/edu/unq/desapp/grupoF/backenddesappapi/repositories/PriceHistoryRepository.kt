package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceHistoryRepository : JpaRepository<PriceHistory, Long> {
    fun findByCryptocurrency(cryptocurrency: CryptoSymbol): List<PriceHistory>
}