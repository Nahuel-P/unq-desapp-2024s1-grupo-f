package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import jakarta.persistence.*
import kotlin.math.max

@Entity(name = "users")
class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var firstName: String? = null

    var lastName: String? = null

    @Column(unique = true)
    var email: String? = null

    var address: String? = null

    var password: String? = null

    var cvu: String? = null

    var walletAddress: String? = null

    var transactions: Int = 0

    var score: Int = 0

    fun decreaseScore(): User {
        score = max(0, score - 20)
        return this
    }

    fun increaseScore(increment: Int): User {
        score += increment
        return this
    }

    fun increaseTransactions(): User {
        transactions++
        return this
    }

    fun reputation(): String {
        if (transactions == 0) {
            return "No trades yet."
        }else{
            return (score/transactions).toString()
        }
    }
}