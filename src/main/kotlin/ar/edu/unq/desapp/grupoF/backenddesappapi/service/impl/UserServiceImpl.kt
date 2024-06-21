package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.mapper.UserMapper
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IOrderService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ITransactionService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserResponseDTO
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

//    override fun getUser(id: Long): User {
//        return commonService.getUser(id)
//    }

    override fun update(user: User): User {
        return userRepository.save(user)
    }

    override fun getOperatedVolumeBy(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime): UserVolumeReport {
        val user = commonService.getUser(userId)
        val transactions = commonService.getTransaction(userId)  // Aquí deberías ajustar tu lógica para obtener las transacciones del usuario en un rango de fechas
        return UserVolumeReport()
    }
}