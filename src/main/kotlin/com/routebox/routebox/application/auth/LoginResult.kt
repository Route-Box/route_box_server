package com.routebox.routebox.application.auth

import com.routebox.routebox.domain.user.LoginType
import com.routebox.routebox.security.JwtInfo

data class LoginResult(
    val isNew: Boolean,
    val loginType: LoginType,
    val accessToken: JwtInfo,
    val refreshToken: JwtInfo,
)
