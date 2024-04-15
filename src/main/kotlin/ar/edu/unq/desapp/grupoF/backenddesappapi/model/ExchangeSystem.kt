package ar.edu.unq.desapp.grupoF.backenddesappapi.model

class ExchangeSystem {

    var users: MutableSet<User> = mutableSetOf()
    var orders: MutableSet<Order> = mutableSetOf()
    var transactions: MutableSet<Transaction> = mutableSetOf()
    var cryptocurrencies: MutableSet<Cryptocurrency>? = null

}