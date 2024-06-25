package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO

class UserMapper {
    companion object {
        fun toDTO(user: User): UserResponseDTO {
            return UserResponseDTO(
                user.id!!,
                user.firstName!!,
                user.lastName!!,
                user.email!!,
                user.reputation()
            )
        }
        
        fun toModel(userDTO: UserCreateDTO): User {
            return UserBuilder()
                .withFirstName(userDTO.firstName!!)
                .withLastName(userDTO.lastName!!)
                .withEmail(userDTO.email!!)
                .withAddress(userDTO.address!!)
                .withPassword(userDTO.password!!)
                .withCvu(userDTO.cvu!!)
                .withWalletAddress(userDTO.walletAddress!!)
                .build()
        }
    }
}