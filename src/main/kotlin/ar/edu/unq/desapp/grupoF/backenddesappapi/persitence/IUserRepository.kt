package ar.edu.unq.desapp.grupoF.backenddesappapi.persitence

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange

interface IUserRepository {
    fun registerUser(user: UserExchange) : UserExchange
    fun getUsers(): Map<Long, UserExchange>
}