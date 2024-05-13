package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(private val userRepository: UserRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        userRepository.save(UserBuilder().withAddress("Av. siempre viva 742").withEmail("test1@gmail.com").withFirstName("Test1").withLastName("User").build())
        userRepository.save(UserBuilder().withAddress("Calle falsa 123").withEmail("test2@gmail.com").withFirstName("Test2").withLastName("User").build())
    }
}