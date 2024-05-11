package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO

interface UserService {
    fun registerUser(user: User): User
    fun getUsers(): List<User>
    fun findUser(id: Long): User
}