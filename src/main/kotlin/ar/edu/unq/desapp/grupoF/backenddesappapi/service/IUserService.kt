package ar.edu.unq.desapp.grupoF.backenddesappapi.service

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import java.time.LocalDateTime

interface IUserService {
    fun getUsers(): List<User>
    fun getUser(id: Long): User
    fun update(user: User): User
    fun getOperatedVolumeBy(userId: Long, startDateTime: LocalDateTime, endDateTime: LocalDateTime): UserVolumeReport
}