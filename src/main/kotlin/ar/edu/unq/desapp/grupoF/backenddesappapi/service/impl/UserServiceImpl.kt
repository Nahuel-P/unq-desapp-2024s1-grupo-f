package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val commonService: ICommonService
) : IUserService {

    val logger: Logger = LogManager.getLogger(UserMapper::class.java)

    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { Exception("User with id $id not found") }
    }

    override fun update(user: User): User {
        return userRepository.save(user)
    }

    override fun getOperatedVolumeBy(
        userId: Long,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime
    ): UserVolumeReport {
        val user = commonService.getUser(userId)
        val transactions = commonService.getTransactionBy(user.id!!, startDateTime, endDateTime)
        return userVolume(transactions)
    }

    private fun userVolume(transactions: List<Transaction>): UserVolumeReport {
        val userVolumeReport = UserVolumeReport()

        transactions.forEach { transaction ->
            userVolumeReport.addToVolumeReport(transaction)
        }
        return userVolumeReport
    }
}