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

    fun publishOrder(order: Order) {
        isUserRegistered(order.ownerUser!!)
        orders.add(order)
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


}