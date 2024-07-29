package com.routebox.routebox.controller.auth.dto

import com.routebox.routebox.application.auth.dto.LoginResult
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.security.JwtInfo
import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @Schema(description = "신규 유저인지 아닌지에 대한 정보. <code>true</code>인 경우 회원가입 절차 수행이 필요한 신규 회원임.")
    val isNew: Boolean,

    @Schema(description = "로그인 종류")
    val loginType: LoginType,

    @Schema(description = "로그인 후 발급된 access token의 value와 만료 시각 정보")
    val accessToken: JwtInfo,

    @Schema(description = "로그인 후 발급된 refresh token의 value와 만료 시각 정보")
    val refreshToken: JwtInfo,
) {
    companion object {
        fun from(loginResult: LoginResult): LoginResponse = LoginResponse(
            isNew = loginResult.isNew,
            loginType = loginResult.loginType,
            accessToken = loginResult.accessToken,
            refreshToken = loginResult.refreshToken,
        )
    }
}
