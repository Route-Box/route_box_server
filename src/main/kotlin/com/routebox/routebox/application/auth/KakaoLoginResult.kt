package com.routebox.routebox.application.auth

import com.routebox.routebox.security.JwtInfo

data class KakaoLoginResult(
    val isNew: Boolean,
    val accessToken: JwtInfo,
    val refreshToken: JwtInfo,
)
