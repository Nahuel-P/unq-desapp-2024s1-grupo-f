package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM transaction t WHERE (t.order.ownerUser.id = :userId OR t.counterParty.id = :userId) AND t.status = 'CONFIRMED' AND t.entryTime BETWEEN :startDate AND :endDate")
    fun findCompletedTransactionsByUserAndBetweenDates(
        @Param("userId") userId: Long,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<Transaction>
}