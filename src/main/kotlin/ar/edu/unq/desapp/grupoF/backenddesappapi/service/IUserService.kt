package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateRequestDTO

interface UserService {
    fun registerUser(userCreateRequest: UserCreateRequestDTO): Any?
}