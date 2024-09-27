package com.routebox.routebox.controller.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class RefreshTokensRequest(
    @Schema(
        description = "토큰 재발급에 사용할, 유효한 refresh token",
        example = "eyJ0eXAiOiJKV1QiLC.eyJzdWIiOiIxIiwicm9sZ.Fp7RNDCv9QQghS2cTC",
    )
    @field:NotBlank
    val refreshToken: String,
)
