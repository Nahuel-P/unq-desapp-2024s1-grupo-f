package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.TransactionBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
class UserServiceImplTest {

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var commonService: ICommonService

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Test
    fun `getUsers returns all users`() {
        val user1 = UserBuilder().build()
        val user2 = UserBuilder().build()
        val users = listOf(user1, user2)
        Mockito.`when`(userRepository.save(user1)).thenReturn(user1)
        Mockito.`when`(userRepository.save(user2)).thenReturn(user2)
        Mockito.`when`(userRepository.findAll()).thenReturn(users)

        val result = userService.getUsers()

        assertEquals(users, result)
    }

    @Test
    fun `getUser returns user by id`() {
        val user = UserBuilder().build()
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val result = userService.getUser(1L)

        assertEquals(user, result)
    }

    @Test
    fun `getUser throws exception when user not found`() {
        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<Exception> { userService.getUser(1L) }
    }

    @Test
    fun `update updates user`() {
        val user = UserBuilder().build()
        Mockito.`when`(userRepository.save(user)).thenReturn(user)

        val result = userService.update(user)

        assertEquals(user, result)
        verify(userRepository).save(user)
    }

    @Test
    fun `getOperatedVolumeBy returns user volume report`() {
        val user = UserBuilder().build()
        user.id = 1L
        val transactions = listOf(TransactionBuilder().build(), TransactionBuilder().build())
        Mockito.`when`(commonService.getUser(1L)).thenReturn(user)
        Mockito.`when`(commonService.getTransactionBy(user.id!!, LocalDateTime.now(), LocalDateTime.now()))
            .thenReturn(transactions)

        val result = userService.getOperatedVolumeBy(1L, LocalDateTime.now(), LocalDateTime.now())

        assertNotNull(result)
    }

}