package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange

interface IUserRepository {
    fun registerUser(user: UserExchange) : UserExchange
    fun getUsers(): Map<Long, UserExchange>
}