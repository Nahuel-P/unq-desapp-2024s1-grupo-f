package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import java.time.LocalDateTime

class CryptocurrencyBuilder {
        private var name: String? = null
        private var createdAt: LocalDateTime? = null

        fun build(): Cryptocurrency {
            val cryptocurrency = Cryptocurrency()
            cryptocurrency.name = this.name
            cryptocurrency.createdAt = this.createdAt
            return cryptocurrency
        }

        fun withName(name: String?): CryptocurrencyBuilder {
            this.name = validateName(name)
            return this
        }

        fun withCreated(date: LocalDateTime?): CryptocurrencyBuilder {
            this.createdAt = date
            return this
        }

        private fun validateName(name: String?): String {
            requireNotNull(name) { "Name cannot be null" }
            return name
        }


}