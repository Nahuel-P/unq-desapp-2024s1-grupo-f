package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import java.time.LocalDateTime

interface IUserService {
//    fun registerUser(userDTO: UserCreateDTO): User
    fun getUsers(): List<User>
    fun getUser(id: Long): User
    fun update(user: User): User
    fun getOperatedVolumeBy(userId: Long, startDateTime: LocalDateTime, endDateTime: LocalDateTime): UserVolumeReport
}