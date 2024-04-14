package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import java.time.LocalDateTime

class CryptocurrencyBuilder {
        private var name: CryptoSymbol? = null
        private var createdAt: LocalDateTime? = null

        fun build(): Cryptocurrency {
            val cryptocurrency = Cryptocurrency()
            cryptocurrency.name = this.name
            cryptocurrency.createdAt = this.createdAt
            return cryptocurrency
        }

        fun withName(name: CryptoSymbol?): CryptocurrencyBuilder {
            this.name = validateName(name)
            return this
        }

        fun withCreated(date: LocalDateTime?): CryptocurrencyBuilder {
            this.createdAt = date
            return this
        }

        private fun validateName(name: CryptoSymbol?): CryptoSymbol {
            requireNotNull(name) { "Name cannot be null" }
            return name
        }


}