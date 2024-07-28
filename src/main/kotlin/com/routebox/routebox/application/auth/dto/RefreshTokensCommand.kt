package com.routebox.routebox.application.auth.dto

data class RefreshTokensCommand(
    val accessToken: String,
    val refreshToken: String,
)
