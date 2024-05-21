package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO

interface IUserService {
    fun registerUser(user: User): UserResponseDTO
    fun getUsers(): List<User>
    fun getUser(id: Long): User
}