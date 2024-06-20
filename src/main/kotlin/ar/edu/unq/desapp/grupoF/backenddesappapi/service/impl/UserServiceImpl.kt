package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserServiceImpl : IUserService{

//    @Autowired
//    private lateinit var transactionService: ITransactionService

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

    override fun update(user: User): User {
        return userRepository.save(user)
    }

    // En UserServiceImpl.kt

    override fun getOperatedVolumeBy(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime): UserVolumeReport {

//        val user = userRepository.findById(userId).get()
////        val transactions = transactionService.findAllByCounterPartyAndEntryTimeBetween(user, startDate, endDate)
//        val transactions = transactionService.getTransactionBy(user.id!!, startDate, endDate)
//
//        val cryptoVolumes = transactions.map { transaction ->
//            val order = transaction.order!!
//            val crypto = order.cryptocurrency!!
//            val currentPriceUsd = cryptoService.getCrypto(crypto.name!!).price
//            val currentPriceArs = currentPriceUsd * dolarExchangeRate // replace with actual exchange rate
//
//            CryptoVolume(
//                cryptoSymbol = crypto.name!!,
//                quantity = order.amount!!,
//                currentPriceUsd = currentPriceUsd,
//                currentPriceArs = currentPriceArs,
//                totalUsd = order.amount!! * currentPriceUsd,
//                totalArs = order.amount!! * currentPriceArs
//            )
//        }
//
//        val totalUsd = cryptoVolumes.sumOf { it.totalUsd }
//        val totalArs = cryptoVolumes.sumOf { it.totalArs }
//
//        return UserVolumeReport(
//            requestDateTime = LocalDateTime.now(),
//            totalUsd = totalUsd,
//            totalArs = totalArs,
//            cryptoVolumes = cryptoVolumes
//        )
        return UserVolumeReport()
    }


}