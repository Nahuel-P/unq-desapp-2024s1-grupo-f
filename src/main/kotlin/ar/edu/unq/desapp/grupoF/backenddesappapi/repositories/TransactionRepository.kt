package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: JpaRepository<Transaction, Long> {
}