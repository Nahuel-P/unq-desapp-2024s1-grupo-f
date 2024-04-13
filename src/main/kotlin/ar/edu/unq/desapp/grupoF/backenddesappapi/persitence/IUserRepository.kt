package ar.edu.unq.desapp.grupoF.backenddesappapi.persitence

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User

interface IUserRepository {
    fun registerUser(user: User) : User
    fun getUsers(): Map<Long, User>
}