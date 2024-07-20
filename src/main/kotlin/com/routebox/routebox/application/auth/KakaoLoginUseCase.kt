package com.routebox.routebox.application.auth

import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.user.LoginType
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.security.JwtManager
import jakarta.validation.Valid
import org.springframework.stereotype.Component

@Component
class KakaoLoginUseCase(
    private val userService: UserService,
    private val authService: AuthService,
    private val jwtManager: JwtManager,
) {
    /**
     * 카카오 로그인
     *
     * @param command
     * @return 로그인 결과로 신규 유저인지에 대한 정보, access token 정보, refresh token 정보를 응답한다.
     */
    operator fun invoke(@Valid command: KakaoLoginCommand): KakaoLoginResult {
        val kakaoUserInfo = authService.getUserInfo(LoginType.KAKAO, command.kakaoAccessToken)

        val user = runCatching {
            userService.createNewUser(LoginType.KAKAO, kakaoUserInfo.uid)
        }.getOrElse { ex ->
            if (ex is UserSocialLoginUidDuplicationException) {
                userService.getUserBySocialLoginUid(kakaoUserInfo.uid)
            } else {
                throw ex
            }
        }

        return KakaoLoginResult(
            isNew = user.createdAt == user.updatedAt,
            accessToken = jwtManager.createAccessToken(user.id, user.roles),
            refreshToken = jwtManager.createRefreshToken(user.id, user.roles),
        )
    }
}
