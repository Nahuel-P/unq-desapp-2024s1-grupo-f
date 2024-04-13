package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.regex.Pattern

class User{

    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var address: String? = null
    var password: String? = null
    var cvu: String? = null
    var walletAddress: String? = null

    private fun validateLength(value: String, min: Int, max: Int, fieldName: String): String {
        require(value.length in min..max) { "$fieldName must be between $min and $max characters" }
        return value
    }

    private fun validateEmail(value: String): String {
        require(Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(value).matches()) { "Invalid email format" }
        return value
    }

    private fun validatePassword(value: String): String {
        require(Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,}$").matcher(value).matches()) { "Password must contain at least 1 lowercase letter, 1 uppercase letter, 1 special character and be at least 6 characters long" }
        return value
    }
    fun publishOrder(intention: IntentionType, cryptocurrency: Cryptocurrency, amount: Double, price: Double): Order {
        return Order(this, cryptocurrency, amount, price, intention)
    }
}