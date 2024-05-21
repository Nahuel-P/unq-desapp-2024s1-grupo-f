package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.OrderBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val cryptocurrencyRepository: CryptocurrencyRepository,
    private val orderRepository: OrderRepository,
    private val transactionRepository: TransactionRepository,
    private val priceHistoryRepository: PriceHistoryRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        generateDataForUsers()
        generateDataForCryptocurrency()
        generateDataForOrders()
        generateDataForTransactions()
        generateDataForPriceHistory()

    }

    fun generateDataForUsers() {
        userRepository.save(
            UserBuilder().withAddress("Av. siempre viva 742").withEmail("test1@gmail.com").withFirstName("Test1")
                .withLastName("User").build()
        )
        userRepository.save(
            UserBuilder().withAddress("Calle falsa 123").withEmail("test2@gmail.com").withFirstName("Test2")
                .withLastName("User").build()
        )
        userRepository.save(
            UserBuilder().withAddress("Calle falsa 456").withEmail("test3@gmail.com").withFirstName("Test3")
                .withLastName("User").build()
        )
    }

    fun generateDataForCryptocurrency() {
        CryptoSymbol.entries.forEach { symbol ->
            val cryptocurrency = Cryptocurrency().apply {
                name = symbol
                createdAt = LocalDateTime.now()
            }
            cryptocurrencyRepository.save(cryptocurrency)
        }
    }

    fun generateDataForOrders() {
        val user1 = userRepository.findById(1L).get()
        val user2 = userRepository.findById(2L).get()

        val order1 = OrderBuilder().withOwnerUser(user1).withAmount(1.0).withPrice(1.0).withType(IntentionType.BUY)
            .withPriceARS(1000.0).build()
        val order2 = OrderBuilder().withOwnerUser(user2).withAmount(2.0).withPrice(2.0).withType(IntentionType.SELL)
            .withPriceARS(2000.0).build()

        orderRepository.save(order1)
        orderRepository.save(order2)
    }

    fun generateDataForTransactions() {
        val user1 = userRepository.findById(1L).get()
        val user2 = userRepository.findById(2L).get()

        val order1 = orderRepository.findById(1L).get()
        val order2 = orderRepository.findById(2L).get()

        val transaction1 = TransactionBuilder().withOrder(order1).withCounterParty(user2).build()

        val transaction2 = TransactionBuilder().withOrder(order2).withCounterParty(user1).build()

        transactionRepository.save(transaction1)
        transactionRepository.save(transaction2)
    }

    fun generateDataForPriceHistory() {
        val optionalCryptocurrency = cryptocurrencyRepository.findByName(CryptoSymbol.BTCUSDT)

        if (optionalCryptocurrency != null) {

            val priceHistory1 = PriceHistory(optionalCryptocurrency, 1000.0)
            val priceHistory2 = PriceHistory(optionalCryptocurrency, 2000.0)

            priceHistoryRepository.save(priceHistory1)
            priceHistoryRepository.save(priceHistory2)
        } else {
            throw Exception("Cryptocurrency not found")
        }
    }
}