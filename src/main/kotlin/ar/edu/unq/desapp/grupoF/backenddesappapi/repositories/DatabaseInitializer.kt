package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.*
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.IntentionType
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.security.SecureRandom
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
        generateDataForPriceHistory()
        generateDataForOrders()
        generateDataForTransactions()
    }

    fun generateDataForUsers() {
        userRepository.save(
            UserBuilder()
                .withFirstName("Homero").withLastName("Simpson").withEmail("hom.jsi@gmail.com").withPassword("Password1!")
                .withAddress("Evergreen Terrace 742").withCvu("0000003100000000046721").withWalletAddress("1A2B3C4D").build()
        )
        userRepository.save(
            UserBuilder()
                .withFirstName("Eric").withLastName("Cartman").withEmail("realcoon@hotmail.com").withPassword("Coon@2024")
                .withAddress("E. Bonanza St. 28201").withCvu("0000007900000000078392").withWalletAddress("5E6F7G8H").build()
        )
        userRepository.save(
            UserBuilder()
                .withFirstName("Peter").withLastName("Griffin").withEmail("petergriffin@quahog.com").withPassword("Hello#123")
                .withAddress("31 Spooner Street").withCvu("0000002100000000023456").withWalletAddress("9I0J1K2L").build()
        )
    }


//    fun generateDataForCryptocurrency() {
//        val prices = BinanceClient().getAllCryptoCurrencyPrices(CryptoSymbol.entries.toMutableList())
//
//        cryptocurrencyRepository.saveAll(prices.map { priceDTO ->
//            CryptocurrencyBuilder()
//                .withName(priceDTO.symbol)
//                .withPrice(priceDTO.price!!)
//                .build()
//        })
//    }
fun generateDataForCryptocurrency() {
    val random = java.util.Random()
    val mockCryptocurrencies = CryptoSymbol.values().map { symbol ->
        CryptocurrencyBuilder()
            .withName(symbol)
            .withPrice(1000.0 + random.nextDouble() * 40000.0)
            .build()
    }

    cryptocurrencyRepository.saveAll(mockCryptocurrencies)
}


    fun generateDataForPriceHistory() {
        val dotusdt = cryptocurrencyRepository.findByName(CryptoSymbol.DOTUSDT)
        val ethusdt = cryptocurrencyRepository.findByName(CryptoSymbol.ETHUSDT)
        val btcusdt = cryptocurrencyRepository.findByName(CryptoSymbol.BTCUSDT)
        val bnbusdt = cryptocurrencyRepository.findByName(CryptoSymbol.BNBUSDT)
        val cakeusdt = cryptocurrencyRepository.findByName(CryptoSymbol.CAKEUSDT)

        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(dotusdt).withPrice(6.89900000).withPriceTime(LocalDateTime.now().minusHours(1)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(dotusdt).withPrice(7.0).withPriceTime(LocalDateTime.now().minusHours(2)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(ethusdt).withPrice(3760.77).withPriceTime(LocalDateTime.now().minusHours(1)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(ethusdt).withPrice(3760.47).withPriceTime(LocalDateTime.now().minusHours(2)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(bnbusdt).withPrice(586.4).withPriceTime(LocalDateTime.now().minusHours(1)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(bnbusdt).withPrice(536.4).withPriceTime(LocalDateTime.now().minusHours(2)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(cakeusdt).withPrice(3.18).withPriceTime(LocalDateTime.now().minusHours(1)).build())
        priceHistoryRepository.save(PriceHistoryBuilder().withCryptocurrency(cakeusdt).withPrice(2.88).withPriceTime(LocalDateTime.now().minusHours(2)).build())

        var initialPrice = 64973.42
        val secureRandom = SecureRandom()
        // Recorremos cada 10 minutos de las últimas 30 horas
        for (i in 1..(30 * 6)) {
            val priceTime = LocalDateTime.now().minusMinutes(10L * i)
            val priceChange = -0.3 + secureRandom.nextDouble() * (0.5 - (-0.3))
            val price = initialPrice + priceChange

            priceHistoryRepository.save(PriceHistoryBuilder()
                .withCryptocurrency(btcusdt)
                .withPrice(price)
                .withPriceTime(priceTime)
                .build())
            initialPrice = price
        }
    }

    fun generateDataForOrders() {
        val user1 = userRepository.findById(1L).orElse(null)
        val user2 = userRepository.findById(2L).orElse(null)
        val btcusdt = cryptocurrencyRepository.findByName(CryptoSymbol.BTCUSDT)
        val bnbusdt = cryptocurrencyRepository.findByName(CryptoSymbol.BNBUSDT)
        val cakeusdt = cryptocurrencyRepository.findByName(CryptoSymbol.CAKEUSDT)

        if (user1 != null && btcusdt != null) {
            orderRepository.save(
                OrderBuilder()
                    .withOwnerUser(user1)
                    .withCryptocurrency(btcusdt)
                    .withAmount(0.02)
                    .withPrice(68315.99)
                    .withType(IntentionType.BUY)
                    .withPriceARS((0.02*68315.99)*1270.0).build()
            )
        }

        if (user2 != null && btcusdt != null) {
            orderRepository.save(
                OrderBuilder()
                    .withOwnerUser(user2)
                    .withCryptocurrency(btcusdt)
                    .withAmount(1.0)
                    .withPrice(68314.0)
                    .withType(IntentionType.BUY)
                    .withPriceARS((1.0*68315.99)*1270.0).build()
            )
        }

        if (user2 != null && bnbusdt != null) {
            orderRepository.save(
                OrderBuilder()
                    .withOwnerUser(user2)
                    .withCryptocurrency(bnbusdt)
                    .withAmount(1.0)
                    .withPrice(595.10)
                    .withType(IntentionType.SELL)
                    .withPriceARS((1.0*595.10)*1270.0).build()
            )
        }

        if (user2 != null && cakeusdt != null) {
            orderRepository.save(
                OrderBuilder()
                    .withOwnerUser(user2)
                    .withCryptocurrency(cakeusdt)
                    .withAmount(2.0)
                    .withPrice(5.54)
                    .withType(IntentionType.SELL)
                    .withPriceARS((2.0*5.54)*1270.0).build()
            )
        }
    }

    fun generateDataForTransactions() {
        val user1 = userRepository.findById(1L).orElse(null)
        val user2 = userRepository.findById(2L).orElse(null)

        val order1 = orderRepository.findById(1L).orElse(null)
        val order2 = orderRepository.findById(2L).orElse(null)

        if (user1 != null && user2 != null && order1 != null && order2 != null) {
            val transaction1 = TransactionBuilder().withOrder(order1).withCounterParty(user2).withEntryTime(LocalDateTime.now().minusHours(2)).build()
            transaction1.order!!.disable()
            val transaction2 = TransactionBuilder().withOrder(order2).withCounterParty(user1).build()
            transaction2.order!!.disable()

            transactionRepository.save(transaction1)
            orderRepository.save(order1)
            transactionRepository.save(transaction2)
            orderRepository.save(order2)
        }
    }
}