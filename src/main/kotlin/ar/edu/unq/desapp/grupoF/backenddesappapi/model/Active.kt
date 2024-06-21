package ar.edu.unq.desapp.grupoF.backenddesappapi.model

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol

class Active {
    var crypto: CryptoSymbol? = null
    var cryptoNominalQuantity: Double = 0.0
    var usdPrice: Double = 0.0
    var argPrice: Double = 0.0
}
