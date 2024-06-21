package ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Active
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol

class ActiveBuilder {
    private var crypto: CryptoSymbol? = null
    private var cryptoNominalQuantity: Double = 0.0
    private var usdPrice: Double = 0.0
    private var argPrice: Double = 0.0

    fun build(): Active {
        val active = Active()
        active.crypto = this.crypto
        active.cryptoNominalQuantity = this.cryptoNominalQuantity
        active.usdPrice = this.usdPrice
        active.argPrice = this.argPrice
        return active
    }

    fun withCrypto(crypto: CryptoSymbol): ActiveBuilder {
        this.crypto = crypto
        return this
    }

    fun withCriptoNominalQuantity(criptoNominalQuantity: Double): ActiveBuilder {
        this.cryptoNominalQuantity = criptoNominalQuantity
        return this
    }

    fun withUsdPrice(usdPrice: Double): ActiveBuilder {
        this.usdPrice = usdPrice
        return this
    }

    fun withArgPrice(argPrice: Double): ActiveBuilder {
        this.argPrice = argPrice
        return this
    }
}