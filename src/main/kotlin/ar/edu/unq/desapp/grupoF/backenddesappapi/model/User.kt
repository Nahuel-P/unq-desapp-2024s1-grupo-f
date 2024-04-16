package ar.edu.unq.desapp.grupoF.backenddesappapi.model

class User{
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var address: String? = null
    var password: String? = null
    var cvu: String? = null
    var walletAddress: String? = null

    var successfulTransaction: Int = 0
    var score: Int = 0

    fun decreaseReputation() {
        score -= 20
    }

    fun increaseReputation(increment: Int) {
        score += increment
    }

}