package ar.edu.unq.desapp.grupoF.backenddesappapi.model

class User {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var address: String? = null
    var password: String? = null
    var cvu: String? = null
    var walletAddress: String? = null

    var successfulTransaction: Int = 0
    var score: Int = 0

    fun decreaseScore(): User {
        score -= 20
        return this
    }

    fun increaseScore(increment: Int): User {
        score += increment
        return this
    }

    fun increaseTransactions(): User {
        successfulTransaction++
        return this
    }

    fun reputation(): Int {
        if (successfulTransaction == 0) {
            return score
        }
        return score / successfulTransaction
    }


}