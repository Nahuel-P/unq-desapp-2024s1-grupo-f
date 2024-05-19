package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CryptocurrencyRepository : JpaRepository<Cryptocurrency, Long>{
    fun findByName(name: CryptoSymbol): Cryptocurrency?
}