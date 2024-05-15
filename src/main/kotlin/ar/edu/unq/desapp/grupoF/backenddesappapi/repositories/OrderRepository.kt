package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUserId(userId: Long): List<Order>
    fun findByIsActiveTrue(): List<Order>
}
