package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(UserMapper::class.java)

class UserMapper {
    companion object {
        fun userToDTO(user: User): UserResponseDTO {
            logger.info("User with email ${user.email} registered")
            return UserResponseDTO(
                user.id!!,
                user.firstName!!,
                user.lastName!!,
                user.email!!,
            )
        }

        fun toModel(userDTO: UserCreateDTO): User {
            return UserBuilder().withFirstName(userDTO.firstName!!)
                .withLastName(userDTO.lastName!!)
                .withEmail(userDTO.email!!)
                .withPassword(userDTO.password!!)
                .build()
        }
    }
}