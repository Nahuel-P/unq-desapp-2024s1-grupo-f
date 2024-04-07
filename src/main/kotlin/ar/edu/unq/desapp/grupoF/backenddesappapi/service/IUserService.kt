package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange

interface IUserService {
    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        address: String,
        password: String,
        initialCvu: String,
        initialWallet: String // TODO: Impl DTO instead of passing values as parameters
    ): UserExchange
}