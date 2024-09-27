package com.routebox.routebox.controller.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class KakaoLoginRequest(
    @Schema(description = "카카오에서 발급받은 access token", example = "G9cWhWDyggpoTE4_rOa6YJCwHuqAAQo8JJkAAAGQc86SkXrb0")
    @field:NotBlank
    val kakaoAccessToken: String,
)
