package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Cryptocurrency
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DatabaseInitializer(
    private val userRepository: UserRepository,
    private val cryptocurrencyRepository: CryptocurrencyRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        generateDataForUsers()
        generateDataForCryptocurrency()
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
    }

    fun generateDataForCryptocurrency(){
        CryptoSymbol.entries.forEach { symbol ->
            val cryptocurrency = Cryptocurrency().apply {
                name = symbol
                createdAt = LocalDateTime.now()
            }
            cryptocurrencyRepository.save(cryptocurrency)
        }
    }
}