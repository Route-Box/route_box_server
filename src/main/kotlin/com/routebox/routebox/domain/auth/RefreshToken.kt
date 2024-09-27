package com.routebox.routebox.domain.auth

// RTR(Refresh token rotation) 사용
data class RefreshToken(
    // key
    val userId: Long,

    // value
    val token: String,
)
