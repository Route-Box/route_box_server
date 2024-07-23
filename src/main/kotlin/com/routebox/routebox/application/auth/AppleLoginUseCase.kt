package com.routebox.routebox.application.auth

import com.routebox.routebox.application.auth.dto.AppleLoginCommand
import com.routebox.routebox.application.auth.dto.LoginResult
import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.security.JwtManager
import jakarta.validation.Valid
import org.springframework.stereotype.Component

@Component
class AppleLoginUseCase(
    private val userService: UserService,
    private val authService: AuthService,
    private val jwtManager: JwtManager,
) {
    /**
     * 애플 로그인.
     *
     * Social login uid를 조회한 후, 다음 로직을 수행한다.
     * - 신규 유저라면: 유저 데이터 생성 및 저장
     * - 기존 유저라면: 유저 데이터 조회
     *
     * 이후 생성 또는 조회한 유저 정보로 access token과 refresh token을 생성하여 반환한다.
     *
     * @param command
     * @return 로그인 결과로 신규 유저인지에 대한 정보, access token 정보, refresh token 정보를 응답한다.
     */
    operator fun invoke(@Valid command: AppleLoginCommand): LoginResult {
        val appleUserInfo = authService.getUserInfo(LoginType.APPLE, command.idToken)

        val user = runCatching {
            userService.createNewUser(LoginType.APPLE, appleUserInfo.uid)
        }.getOrElse { ex ->
            if (ex is UserSocialLoginUidDuplicationException) {
                userService.getUserBySocialLoginUid(appleUserInfo.uid)
            } else {
                throw ex
            }
        }

        return LoginResult(
            isNew = user.createdAt == user.updatedAt,
            loginType = LoginType.APPLE,
            accessToken = jwtManager.createAccessToken(user.id, user.roles),
            refreshToken = jwtManager.createRefreshToken(user.id, user.roles),
        )
    }
}