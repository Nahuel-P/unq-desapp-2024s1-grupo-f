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
}