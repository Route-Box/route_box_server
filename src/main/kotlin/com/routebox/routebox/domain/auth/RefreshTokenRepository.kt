package com.routebox.routebox.domain.auth

interface RefreshTokenRepository {
    fun findRefreshToken(userId: Long): RefreshToken?

    fun save(refreshToken: RefreshToken)
}
