package com.routebox.routebox.security

import java.time.LocalDateTime

data class JwtInfo(
    val token: String,
    val expiresAt: LocalDateTime,
)
