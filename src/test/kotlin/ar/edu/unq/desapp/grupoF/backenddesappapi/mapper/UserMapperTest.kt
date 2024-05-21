package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserMapperTest {
    @Test
    fun `userToDTO converts User to DTO`() {
        val user = mock(User::class.java)
        `when`(user.id).thenReturn(1L)
        `when`(user.firstName).thenReturn("Michael")
        `when`(user.lastName).thenReturn("Scott")
        `when`(user.email).thenReturn("michael.scott32@test.com")

        val dto = UserMapper.userToDTO(user)

        assertEquals(1L, dto.id)
        assertEquals("Michael", dto.firstName)
        assertEquals("Scott", dto.lastName)
        assertEquals("michael.scott32@test.com", dto.email)
    }

    @Test
    fun `toModel converts DTO to User`() {
        val dto = mock(UserCreateDTO::class.java)
        `when`(dto.firstName).thenReturn("Michael")
        `when`(dto.lastName).thenReturn("Scott")
        `when`(dto.email).thenReturn("michael.scott32@test.com")
        `when`(dto.password).thenReturn("Password1!")

        val user = UserMapper.toModel(dto)

        assertEquals("Michael", user.firstName)
        assertEquals("Scott", user.lastName)
        assertEquals("michael.scott32@test.com", user.email)
        assertEquals("Password1!", user.password)
    }
}