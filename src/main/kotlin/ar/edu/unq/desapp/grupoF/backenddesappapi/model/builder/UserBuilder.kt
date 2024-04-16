package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import java.util.regex.Pattern

class UserBuilder {
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var address: String? = null
    private var password: String? = null
    private var cvu: String? = null
    private var walletAddress: String? = null

    fun build(): User {
        val user = User()
        user.firstName = this.firstName
        user.lastName = this.lastName
        user.email = this.email
        user.address = this.address
        user.password = this.password
        user.cvu = this.cvu
        user.walletAddress = this.walletAddress
        return user
    }

    fun withFirstName(firstName: String): UserBuilder {
        this.firstName = validateLength(firstName, 3, 30, "First name")
        return this
    }

    fun withLastName(lastName: String): UserBuilder {
        this.lastName = validateLength(lastName, 3, 30, "Last name")
        return this
    }

    fun withEmail(email: String): UserBuilder {
        this.email = validateEmail(email)
        return this
    }

    fun withAddress(address: String): UserBuilder {
        this.address = validateLength(address, 10, 30, "Address")
        return this
    }

    fun withPassword(password: String): UserBuilder {
        this.password = validatePassword(password)
        return this
    }

    fun withCvu(cvu: String): UserBuilder {
        this.cvu = validateLength(cvu, 22, 22, "CVU")
        return this
    }

    fun withWalletAddress(walletAddress: String): UserBuilder {
        this.walletAddress = validateLength(walletAddress, 8, 8, "Wallet")
        return this
    }

    private fun validateLength(value: String, min: Int, max: Int, fieldName: String): String {
        require(value.length in min..max) { "$fieldName must be between $min and $max characters" }
        return value
    }

    private fun validateEmail(value: String): String {
        require(Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(value).matches()) { "Invalid email format" }
        return value
    }

    private fun validatePassword(value: String): String {
        require(
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,}$").matcher(value).matches()
        ) { "Password must contain at least 1 lowercase letter, 1 uppercase letter, 1 special character and be at least 6 characters long" }
        return value
    }
}