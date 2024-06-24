package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.LoginRequestDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO

interface IAuthService {
    fun registerUser(userDto: UserCreateDTO): User
    fun login(loginDTO: LoginRequestDTO): String?

}