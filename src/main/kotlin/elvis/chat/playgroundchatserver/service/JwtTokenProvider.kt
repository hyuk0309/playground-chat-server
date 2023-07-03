package elvis.chat.playgroundchatserver.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

private const val TOKEN_VALID_MILISECOND = 1000L * 60 * 60 // one hour
private val log = KotlinLogging.logger { }

@Component
class JwtTokenProvider {

    @Value("\${spring.jwt.secret}")
    private lateinit var secreteKey: String

    fun generateToken(name: String): String {
        val now = Date()
        return Jwts.builder()
            .setId(name)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + TOKEN_VALID_MILISECOND))
            .signWith(SignatureAlgorithm.HS256, secreteKey)
            .compact()
    }

    fun getUserNameFromJwt(jwt: String): String {
        return getClaims(jwt)?.body?.id!!
    }

    fun validateToken(jwt: String?): Boolean {
        return this.getClaims(jwt) != null
    }

    private fun getClaims(jwt: String?): Jws<Claims>? {
        try {
            return Jwts.parser().setSigningKey(secreteKey).parseClaimsJws(jwt)
        } catch (ex: SignatureException) {
            log.error { "Invalid JWT signature" }
            throw ex
        } catch (ex: MalformedJwtException) {
            log.error { "Invalid JWT token" }
            throw ex
        } catch (ex: ExpiredJwtException) {
            log.error { "Expired JWT token" }
            throw ex
        } catch (ex: UnsupportedJwtException) {
            log.error { "Unsupported JWT token" }
            throw ex
        } catch (ex: IllegalArgumentException) {
            log.error { "JWT claims string is empty." }
            throw ex
        }
    }
}