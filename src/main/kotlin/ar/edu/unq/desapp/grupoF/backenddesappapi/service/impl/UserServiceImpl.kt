package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.springframework.stereotype.Service


@Service
class UserServiceImpl( private val userRepository: UserRepository) : UserService{
//    private val objectMapper = jacksonObjectMapper()

    override fun registerUser(userCreateRequest: UserCreateRequestDTO): User {
//        if (userCreateRequest.email == null || userCreateRequest.email!!.isEmpty()) {
//            return objectMapper.writeValueAsString(mapOf("message" to "Email must not be null or empty"))
//        }
        val newUser = UserBuilder()
                    .withFirstName(userCreateRequest.firstName!!)
                    .withLastName(userCreateRequest.lastName!!)
                    .withAddress(userCreateRequest.address!!)
                    .withPassword(userCreateRequest.password!!)
                    .withCvu(userCreateRequest.cvu!!)
                    .withWalletAddress(userCreateRequest.walletAddress!!)
                    .build()

        newUser.email = userCreateRequest.email
//      // to do
//        return user.firstName?.let { user.email?.let { it1 -> objectMapper.writeValueAsString(UserResponseDTO(it, it1)) } }
//            ?: objectMapper.writeValueAsString(mapOf("message" to "User registration failed"))

        return userRepository.save(newUser)
    }
}