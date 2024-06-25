package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenServiceImpl {

    val secretKey = "c4aeb476ac78587f3d1a9f2dbe639ba2f1278e92b75e3c6a1e94b1a9f1dc9b18"

    fun generateJWT(user: User): String {
        var token: String = Jwts
            .builder()
            .subject(user.email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(getSigninKey())
            .compact()
        return token
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSigninKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun <T> extractClaim(token: String, resolver: (claim: Claims) -> T): T {
        val claims: Claims = extractAllClaims(token)
        return resolver(claims)
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun getSigninKey(): SecretKey {
        val keyBytes = Decoders.BASE64URL.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun isValid(token: String, user: UserDetails): Boolean {
        val username: String = extractUsername(token)
        return username == user.username && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

}