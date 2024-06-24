package ar.edu.unq.desapp.grupoF.backenddesappapi.security

import ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@SpringBootTest
class JwtAuthenticationFilterTest {

        @Mock
        lateinit var request: HttpServletRequest

        @Mock
        lateinit var response: HttpServletResponse

        @Mock
        lateinit var filterChain: FilterChain

        @Mock
        lateinit var jwtService: TokenService

        @InjectMocks
        lateinit var jwtAuthenticationFilter: TestableJwtAuthenticationFilter

        @Mock
        lateinit var userDetailsServiceImpl: UserDetailsService


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `doFilterInternal should continue filter chain when authorization header is missing`() {
        Mockito.`when`(request.getHeader("Authorization")).thenReturn(null)

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        Mockito.verify(filterChain).doFilter(request, response)
    }


    @Test
    fun `doFilterInternal should continue filter chain when authorization header is null`() {
        Mockito.`when`(request.getHeader("Authorization")).thenReturn(null)

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        Mockito.verify(filterChain).doFilter(request, response)
    }

    @Test
    fun `doFilterInternal should continue filter chain when authorization header does not start with Bearer`() {
        Mockito.`when`(request.getHeader("Authorization")).thenReturn("Invalid")

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        Mockito.verify(filterChain).doFilter(request, response)
    }

    @Test
    fun `doFilterInternal should continue filter chain when jwt token is invalid`() {
        val authHeader = "Bearer token"
        val username = "username"
        val userDetails: UserDetails = Mockito.mock(UserDetails::class.java)

        Mockito.`when`(request.getHeader("Authorization")).thenReturn(authHeader)
        Mockito.`when`(jwtService.extractUsername("token")).thenReturn(username)
        Mockito.`when`(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails)
        Mockito.`when`(jwtService.isValid("token", userDetails)).thenReturn(false)

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        Mockito.verify(filterChain).doFilter(request, response)
    }

    @Test
    fun `doFilterInternal should not set authentication when SecurityContextHolder already has an authentication`() {
        val authHeader = "Bearer token"
        val username = "username"
        val userDetails: UserDetails = Mockito.mock(UserDetails::class.java)
        val existingAuth: Authentication = Mockito.mock(Authentication::class.java)

        SecurityContextHolder.getContext().authentication = existingAuth

        Mockito.`when`(request.getHeader("Authorization")).thenReturn(authHeader)
        Mockito.`when`(jwtService.extractUsername("token")).thenReturn(username)
        Mockito.`when`(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails)
        Mockito.`when`(jwtService.isValid("token", userDetails)).thenReturn(true)

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        assertEquals(existingAuth, SecurityContextHolder.getContext().authentication)
    }

    @Test
    fun `doFilterInternal should not set authentication when jwt token is valid but jwtService isValid returns false`() {
        val authHeader = "Bearer token"
        val username = "username"
        val userDetails: UserDetails = Mockito.mock(UserDetails::class.java)

        Mockito.`when`(request.getHeader("Authorization")).thenReturn(authHeader)
        Mockito.`when`(jwtService.extractUsername("token")).thenReturn(username)
        Mockito.`when`(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails)
        Mockito.`when`(jwtService.isValid("token", userDetails)).thenReturn(false)

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        assertNull(SecurityContextHolder.getContext().authentication)
    }

    @Test
    fun `doFilterInternal should set authentication when jwt token is valid`() {
        val authHeader = "Bearer token"
        val username = "username"
        val userDetails: UserDetails = Mockito.mock(UserDetails::class.java)

        Mockito.`when`(request.getHeader("Authorization")).thenReturn(authHeader)
        Mockito.`when`(jwtService.extractUsername("token")).thenReturn(username)
        Mockito.`when`(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails)
        Mockito.`when`(jwtService.isValid("token", userDetails)).thenReturn(true)

        jwtAuthenticationFilter.testDoFilterInternal(request, response, filterChain)

        val authentication = SecurityContextHolder.getContext().authentication
        assertTrue(authentication is UsernamePasswordAuthenticationToken)
        assertEquals(userDetails, authentication.principal)
        assertEquals(userDetails.authorities, authentication.authorities)
    }

    class TestableJwtAuthenticationFilter : JwtAuthenticationFilter() {
        fun testDoFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
            this.doFilterInternal(request, response, filterChain)
        }
    }

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
    }
}