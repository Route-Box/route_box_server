package com.routebox.routebox.security

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class JwtInfo(
    @Schema(description = "token value", example = "eyJ0eXAiOiJKV1QiLC.eyJzdWIiOiIxIiwicm9sZ.Fp7RNDCv9QQghS2cTC")
    val token: String,

    @Schema(description = "토큰 만료 시각")
    val expiresAt: LocalDateTime,
)
