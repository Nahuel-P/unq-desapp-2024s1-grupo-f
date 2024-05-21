package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserServiceImpl : IUserService{

    @Autowired
    private lateinit var userRepository: UserRepository
    override fun registerUser(userDTO: UserCreateDTO): User {
        if (userRepository.existsByEmail(userDTO.email!!)) {
            throw Exception("User with email ${userDTO.email} already exists")
        }
        val user = UserMapper.toModel(userDTO)
        return userRepository.save(user)
    }

    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { Exception("User with id $id not found") }
    }


}