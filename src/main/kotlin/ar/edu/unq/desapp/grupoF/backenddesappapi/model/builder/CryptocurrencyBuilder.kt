package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol

class CryptocurrencyBuilder {

    private var name: CryptoSymbol? = null
    private var price: Double = 0.00

    fun build(): Cryptocurrency {
        val cryptocurrency = Cryptocurrency()
        cryptocurrency.name = this.name
        cryptocurrency.price = this.price
        return cryptocurrency
    }

    fun withName(name: CryptoSymbol?): CryptocurrencyBuilder {
        this.name = validateName(name)
        return this
    }

    fun withPrice(price: Double): CryptocurrencyBuilder {
        this.price = validatePrice(price)
        return this
    }

    private fun validateName(name: CryptoSymbol?): CryptoSymbol {
        requireNotNull(name) { "Name cannot be null" }
        return name
    }

    private fun validatePrice(price: Double?): Double {
        requireNotNull(price) { "Price cannot be null" }
        require(price >= 0) { "Price cannot be negative" }
        return price
    }

}