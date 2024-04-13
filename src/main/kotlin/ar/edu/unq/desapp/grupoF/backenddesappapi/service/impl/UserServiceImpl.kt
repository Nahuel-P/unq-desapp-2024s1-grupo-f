//package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl
//
//import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
//import ar.edu.unq.desapp.grupoF.backenddesappapi.persitence.IUserRepository
//import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//
//@Service
//class UserServiceImpl : IUserService {
//
//    @Autowired
//    private lateinit var userRepository: IUserRepository
//
//    override fun registerUser(
//        firstName: String,
//        lastName: String,
//        email: String,
//        address: String,
//        password: String,
//        initialCvu: String,
//        initialWallet: String
//    ): User {
//        val user = User(firstName, lastName, email, address, password, initialCvu, initialWallet)
//        return userRepository.registerUser(user)
//    }
//
//}