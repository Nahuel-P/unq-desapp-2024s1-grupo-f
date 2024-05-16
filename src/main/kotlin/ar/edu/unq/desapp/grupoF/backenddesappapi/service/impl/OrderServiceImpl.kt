package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Order
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.CryptocurrencyBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.CryptocurrencyRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : IOrderService {
    override fun createOrder(user: User,
                             cryptocurrency: String,
                             amount: Double,
                             price: Double,
                             type: String) : Order {

        val cryptoSymbol = CryptoSymbol.valueOf(cryptocurrency.uppercase(Locale.getDefault()))
        val crypto = CryptocurrencyBuilder().withName(cryptoSymbol).build()
        cryptocurrencyRepository.save(crypto)
        val intentionType = IntentionType.valueOf(type.uppercase(Locale.getDefault()))

        val order = OrderBuilder()
            .withOwnerUser(user)
            .withCryptocurrency(crypto)
            .withAmount(amount)
            .withPrice(price)
            .withType(intentionType)
            .build()
        orderRepository.save(order)
        return order
    }

    override fun getActiveOrders(): List<Order> {
        return orderRepository.findByIsActiveTrue()
    }

    override fun getOrder(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { Exception("Order with id $id not found") }
    }
}