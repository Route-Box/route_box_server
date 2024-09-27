package com.routebox.routebox.security

import com.routebox.routebox.domain.user.constant.UserRoleType
import com.routebox.routebox.exception.security.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.ZoneId
import java.util.Date

@Component
class JwtManager(@Value("\${routebox.jwt.secret-key}") private val salt: String) {
    companion object {
        private const val MILLISECONDS_IN_MINUTE: Long = 1000 * 60
        private const val MILLISECONDS_IN_HOUR: Long = 60 * MILLISECONDS_IN_MINUTE
        private const val MILLISECONDS_IN_DAY: Long = 24 * MILLISECONDS_IN_HOUR
        private const val ACCESS_TOKEN_EXPIRED_DURATION_MILLIS: Long = 2 * MILLISECONDS_IN_HOUR
        const val REFRESH_TOKEN_EXPIRED_DURATION_MILLIS: Long = 30 * MILLISECONDS_IN_DAY
        private const val USER_ROLE_CLAIM_KEY = "role"
    }

    private lateinit var secretKey: Key

    @PostConstruct
    fun init() {
        if (salt.isBlank()) {
            throw IllegalArgumentException("JWT token 생성에 필요한 secret key(salt)가 입력되지 않았습니다.")
        }
        secretKey = Keys.hmacShaKeyFor(salt.toByteArray(StandardCharsets.UTF_8))
    }

    /**
     * Access token 생성
     *
     * @param userId PK of user
     * @param userRoles roles of user
     * @return 생성된 access token 정보(토큰 값, 만료 시각)
     * @see ACCESS_TOKEN_EXPIRED_DURATION_MILLIS access token 만료 기한
     */
    fun createAccessToken(userId: Long, userRoles: Set<UserRoleType>): JwtInfo =
        createToken(userId, userRoles, ACCESS_TOKEN_EXPIRED_DURATION_MILLIS)

    /**
     * Refresh token 생성
     *
     * @param userId PK of user
     * @param userRoles roles of user
     * @return 생성된 refresh token 정보(토큰 값, 만료 시각)
     * @see REFRESH_TOKEN_EXPIRED_DURATION_MILLIS refresh token 만료 기한
     */
    fun createRefreshToken(userId: Long, userRoles: Set<UserRoleType>): JwtInfo =
        createToken(userId, userRoles, REFRESH_TOKEN_EXPIRED_DURATION_MILLIS)

    /**
     * JWT 생성
     *
     * @param userId PK of user
     * @param userRoles roles of user
     * @param tokenDuration 토큰 만료 기한
     * @return 생성된 token 정보(토큰 값, 만료 시각)
     */
    private fun createToken(userId: Long, userRoles: Set<UserRoleType>, tokenDuration: Long): JwtInfo {
        val now = Date()
        val expiresAt = Date(now.time + tokenDuration)
        val token = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(userId.toString())
            .claim(USER_ROLE_CLAIM_KEY, userRoles)
            .setIssuedAt(now)
            .setExpiration(expiresAt)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
        return JwtInfo(
            token = token,
            expiresAt = expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        )
    }

    /**
     * JWT로부터 subject(userId)를 추출한다.
     *
     * @param token subject를 추출할 token
     * @return 추출한 subject(userId)
     */
    fun getUserIdFromToken(token: String): Long =
        getClaimsFromToken(token).subject.toLong()

    private fun getClaimsFromToken(token: String): Claims =
        getJwsFromToken(token).body

    private fun getJwsFromToken(token: String): Jws<Claims> {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
        } catch (ex: UnsupportedJwtException) {
            throw InvalidTokenException("The claimsJws argument does not represent an Claims JWS", ex)
        } catch (ex: MalformedJwtException) {
            throw InvalidTokenException("The claimsJws string is not a valid JWS", ex)
        } catch (ex: SignatureException) {
            throw InvalidTokenException("The claimsJws JWS signature validation fails", ex)
        } catch (ex: ExpiredJwtException) {
            throw InvalidTokenException("The Claims has an expiration time before the time this method is invoked.", ex)
        } catch (ex: IllegalArgumentException) {
            throw InvalidTokenException("The claimsJws string is null or empty or only whitespace", ex)
        }
    }

    /**
     * 토큰의 유효성, 만료일자 검증
     *
     * @param token 검증하고자 하는 JWT token
     * @throws InvalidTokenException Token 값이 잘못되거나 만료되어 유효하지 않은 경우
     */
    fun validate(token: String) {
        if (token.isBlank()) {
            throw InvalidTokenException("The token is empty")
        }
        getJwsFromToken(token)
    }
}
