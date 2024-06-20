package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface CryptocurrencyRepository : JpaRepository<Cryptocurrency, Long> {

    fun findByName(name: CryptoSymbol): Cryptocurrency?

    @Query("SELECT p FROM price_history p WHERE p.cryptocurrency.name = :name AND p.priceTime BETWEEN :startDateTime AND :endDateTime")
    fun findPriceHistoryBySymbolAndTimeBetween(name: CryptoSymbol, startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<PriceHistory>
}