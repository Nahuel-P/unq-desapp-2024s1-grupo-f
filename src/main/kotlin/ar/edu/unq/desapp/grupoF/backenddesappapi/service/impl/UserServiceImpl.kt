package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserVolumeReportMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val commonService: ICommonService
) : IUserService {

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

    override fun update(user: User): User {
        return userRepository.save(user)
    }

    override fun getOperatedVolumeBy(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime): UserVolumeReport {
        val user = commonService.getUser(userId)
        val transactions = commonService.getTransactionBy(user.id!!,startDate,endDate)
        return userVolume(transactions)
    }

    private fun userVolume(transactions: List<Transaction>): UserVolumeReport {
        val userVolumeReport = UserVolumeReport()
        var totalUSD = 0.0
        var totalARG = 0.0
//        var actives = mutableListOf<Active>()

        transactions.forEach { transaction ->
            totalUSD += transaction.usdPrice() * transaction.nominalAmount()
            totalARG += transaction.arsQuote()
        }
        return UserVolumeReportMapper.toModel(totalUSD, totalARG)

    }
}