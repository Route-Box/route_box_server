package com.routebox.routebox.application.auth.dto

import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.security.JwtInfo

data class LoginResult(
    val isNew: Boolean,
    val loginType: LoginType,
    val accessToken: JwtInfo,
    val refreshToken: JwtInfo,
)
