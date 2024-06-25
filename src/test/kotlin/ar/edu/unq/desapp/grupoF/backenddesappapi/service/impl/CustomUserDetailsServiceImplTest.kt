package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.webservice.dto.UserDetailsDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.userdetails.UsernameNotFoundException

@SpringBootTest
class CustomUserDetailsServiceImplTest {

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsServiceImpl

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun `loadUserByUsername returns UserDetails when user exists`() {
        val user = UserBuilder().withEmail("test@test.com").build()
        `when`(userRepository.findByEmail(user.email!!)).thenReturn(user)

        val result = customUserDetailsService.loadUserByUsername(user.email)

        assertEquals(UserDetailsDTO(user), result)
    }

    @Test
    fun `loadUserByUsername throws UsernameNotFoundException when user does not exist`() {
        val email = "nonexistent@test.com"
        `when`(userRepository.findByEmail(email)).thenReturn(null)

        assertThrows<UsernameNotFoundException> {
            customUserDetailsService.loadUserByUsername(email)
        }
    }
}