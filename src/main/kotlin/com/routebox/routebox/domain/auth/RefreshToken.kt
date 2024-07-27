package com.routebox.routebox.domain.auth

// RTR(Refresh token rotation) 사용
data class RefreshToken(
    val userId: Long, // key
    val token: String, // value
)
