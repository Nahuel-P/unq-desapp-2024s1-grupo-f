package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.core.userdetails.UserDetails

class TokenServiceImplTest {

    private val tokenService = TokenServiceImpl()

    @Test
    fun `extractAllClaims returns correct claims from token`() {
        val user = UserBuilder().withEmail("test@test.com").build()
        val token = tokenService.generateJWT(user)

        val claims = tokenService.extractAllClaims(token)
        assertEquals(user.email, claims.subject)
    }

    @Test
    fun `extractUsername returns correct username from token`() {
        val user = UserBuilder().withEmail("test@test.com").build()
        val token = tokenService.generateJWT(user)

        val username = tokenService.extractUsername(token)
        assertEquals(user.email, username)
    }

    @Test
    fun `isValid returns true for valid token and user`() {
        val user = UserBuilder().withEmail("test@test.com").build()
        val token = tokenService.generateJWT(user)

        val userDetails = Mockito.mock(UserDetails::class.java)
        Mockito.`when`(userDetails.username).thenReturn(user.email)

        assertTrue(tokenService.isValid(token, userDetails))
    }

    @Test
    fun `isTokenExpired returns false for valid token`() {
        val user = UserBuilder().withEmail("test@test.com").build()
        val token = tokenService.generateJWT(user)

        assertFalse(tokenService.isTokenExpired(token))
    }
}