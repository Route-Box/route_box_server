package com.routebox.routebox.infrastructure.auth

import com.routebox.routebox.domain.auth.RefreshToken
import com.routebox.routebox.domain.auth.RefreshTokenRepository
import com.routebox.routebox.security.JwtManager
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) : RefreshTokenRepository {

    companion object {
        private const val KEY_PREFIX = "refresh_token:"
    }

    override fun save(refreshToken: RefreshToken) {
        redisTemplate.opsForValue().set(
            KEY_PREFIX + refreshToken.userId,
            refreshToken.token,
            JwtManager.REFRESH_TOKEN_EXPIRED_DURATION_MILLIS,
            TimeUnit.MILLISECONDS,
        )
    }
}
