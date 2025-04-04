package com.routebox.routebox.infrastructure.user_mobile

import com.routebox.routebox.domain.user_mobile.UserMobile
import org.springframework.data.jpa.repository.JpaRepository

interface UserMobileRepository : JpaRepository<UserMobile, Long> {
    fun findByUserId(userId: Long): UserMobile?
}
