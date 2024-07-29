package com.routebox.routebox.controller.auth.dto

import com.routebox.routebox.security.JwtInfo
import io.swagger.v3.oas.annotations.media.Schema

data class RefreshTokensResponse(
    @Schema(description = "Access token의 value와 만료 시각 정보")
    val accessToken: JwtInfo,

    @Schema(description = "Refresh token의 value와 만료 시각 정보")
    val refreshToken: JwtInfo,
)
