package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    override fun registerUser(userCreateRequest: UserCreateRequestDTO): User {
        require(!(userCreateRequest.email == null || userCreateRequest.email!!.isEmpty())) { "Email must not be null or empty" }
        val user = UserBuilder()
            .withFirstName(userCreateRequest.firstName!!)
            .withLastName(userCreateRequest.lastName!!)
            .withEmail(userCreateRequest.email!!)
            .withAddress(userCreateRequest.address!!)
            .withPassword(userCreateRequest.password!!)
            .withCvu(userCreateRequest.cvu!!)
            .withWalletAddress(userCreateRequest.walletAddress!!)
            .build()
        return user
    }
}