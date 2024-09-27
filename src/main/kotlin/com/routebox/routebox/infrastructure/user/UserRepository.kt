package com.routebox.routebox.infrastructure.user

import com.routebox.routebox.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findBySocialLoginUid(socialLoginUid: String): User?

    fun existsBySocialLoginUid(socialLoginUid: String): Boolean

    fun existsByNickname(nickname: String): Boolean
}
