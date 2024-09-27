package com.routebox.routebox.application.auth

import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.security.InvalidTokenException
import com.routebox.routebox.security.JwtInfo
import com.routebox.routebox.security.JwtManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RefreshTokensUseCase(
    private val userService: UserService,
    private val authService: AuthService,
    private val jwtManager: JwtManager,
) {
    /**
     * Access, refresh token을 재발급한다.
     *
     * @param refreshToken 토큰 재발급에 사용할, 유효한 refresh token
     * @return access token, refresh token
     * @throws InvalidTokenException token이 유효하지 않은 경우
     */
    @Transactional
    operator fun invoke(refreshToken: String): Pair<JwtInfo, JwtInfo> {
        val userId = jwtManager.getUserIdFromToken(refreshToken)
        checkRefreshTokenAvailability(userId, refreshToken)

        val user = userService.getUserById(userId)
        return Pair(
            authService.issueAccessToken(user),
            authService.issueRefreshToken(user),
        )
    }

    private fun checkRefreshTokenAvailability(userId: Long, refreshToken: String) {
        val availableRefreshToken = authService.findAvailableRefreshToken(userId)

        // 이용 가능한 refresh token이 없는 경우 => 토큰 재발급 불가능 (exception throw)
        // 전달받은 refresh token이 토큰 재발급을 위해 사용 가능한 refresh token인지 검증
        if (availableRefreshToken == null || availableRefreshToken != refreshToken) {
            throw InvalidTokenException()
        }
    }
}
