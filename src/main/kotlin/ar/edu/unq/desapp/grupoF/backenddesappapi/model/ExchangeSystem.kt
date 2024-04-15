package ar.edu.unq.desapp.grupoF.backenddesappapi.model

class ExchangeSystem {

    var users: MutableSet<User> = mutableSetOf()
    var orders: MutableList<Order> = mutableListOf()
    var transactions: MutableList<Transaction> = mutableListOf()
    var cryptocurrencies: MutableSet<Cryptocurrency>? = null

    fun registerUser(user: User) {
        validateUser(user)
        users.add(user)
    }

    fun getPrices(cryptocurrency: Cryptocurrency): List<PriceHistory> {
        val prices = mutableListOf<PriceHistory>()
        for (crypto in cryptocurrencies!!) {
            crypto.lastPrice()?.let { prices.add(it) }
        }
        return prices
    }

    fun currencyFluctuation(cryptocurrency: Cryptocurrency): List<PriceHistory> {
        return cryptocurrency.pricesOver24hs()
    }

    fun publishOrder(order: Order) {
        isUserRegistered(order.ownerUser!!)
        orders.add(order)
    }

        fun ordersByUser(user: User): List<Order> {
            isUserRegistered(user)
            return orders.filter { it.ownerUser == user }
        }

        fun startTransaction(order: Order, counterParty: User): Transaction {
            isUserRegistered(counterParty)
            isRegisteredOrder(order)
            val transaction = Transaction().startTransaction(order, counterParty)
            transactions.add(transaction)
            return transaction
        }

    private fun validateUser(user: User) {
        if (users.any { it.email == user.email }) {
            throw IllegalArgumentException("Email already exists")
        }
    }

    private fun isUserRegistered(ownerUser: User) {
        if (!users.contains(ownerUser)) {
            throw IllegalArgumentException("User is not registered")
        }
    }

    private fun isRegisteredOrder(order: Order) {
        if (!orders.contains(order)) {
            throw IllegalArgumentException("Order is not registered")
        }
    }



}