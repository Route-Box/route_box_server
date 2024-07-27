package com.routebox.routebox.domain.auth

interface RefreshTokenRepository {
    fun save(refreshToken: RefreshToken)
}
