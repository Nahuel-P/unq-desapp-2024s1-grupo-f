package ar.edu.unq.desapp.grupoF.backenddesappapi.mapper

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserCreateDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
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
        `when`(user.reputation()).thenReturn("0")

        val dto = UserMapper.toDTO(user)

        assertEquals(1L, dto.id)
        assertEquals("Michael", dto.firstName)
        assertEquals("Scott", dto.lastName)
        assertEquals("michael.scott32@test.com", dto.email)
        assertEquals("0", dto.reputation)
    }

    @Test
    fun `toModel converts DTO to User`() {
        val dto = mock(UserCreateDTO::class.java)
        `when`(dto.firstName).thenReturn("Michael")
        `when`(dto.lastName).thenReturn("Scott")
        `when`(dto.email).thenReturn("michael.scott32@test.com")
        `when`(dto.password).thenReturn("Password1!")
        `when`(dto.address).thenReturn("1725 Slough Avenue, Scranton")
        `when`(dto.cvu).thenReturn("1234567890123456789012")
        `when`(dto.walletAddress).thenReturn("12345678")

        val user = UserMapper.toModel(dto)

        assertEquals("Michael", user.firstName)
        assertEquals("Scott", user.lastName)
        assertEquals("michael.scott32@test.com", user.email)
        assertEquals("Password1!", user.password)
        assertEquals("1725 Slough Avenue, Scranton", user.address)
        assertEquals("1234567890123456789012", user.cvu)
        assertEquals("12345678", user.walletAddress)
    }

    @Test
    fun `toSellerDTO converts User to SellerDTO`() {
        val user = mock(User::class.java)
        `when`(user.id).thenReturn(1L)
        `when`(user.firstName).thenReturn("Cosme")
        `when`(user.lastName).thenReturn("Fulanito")
        `when`(user.email).thenReturn("cfulanito.@test.com")
        `when`(user.cvu).thenReturn("1234567890123456789012")

        val dto = UserMapper.toSellerDTO(user)

        assertEquals(1L, dto.id)
        assertEquals("Cosme", dto.firstName)
        assertEquals("Fulanito", dto.lastName)
        assertEquals("cfulanito.@test.com", dto.email)
        assertEquals("1234567890123456789012", dto.cvu)
    }

    @Test
    fun `toBuyerDTO converts User to BuyerDTO`() {
        val user = mock(User::class.java)
        `when`(user.id).thenReturn(1L)
        `when`(user.firstName).thenReturn("Cosme")
        `when`(user.lastName).thenReturn("Fulanito")
        `when`(user.email).thenReturn("cfulanito.@test.com")
        `when`(user.walletAddress).thenReturn("12345678")

        val dto = UserMapper.toBuyerDTO(user)

        assertEquals(1L, dto.id)
        assertEquals("Cosme", dto.firstName)
        assertEquals("Fulanito", dto.lastName)
        assertEquals("cfulanito.@test.com", dto.email)
        assertEquals("12345678", dto.wallet)
    }
}