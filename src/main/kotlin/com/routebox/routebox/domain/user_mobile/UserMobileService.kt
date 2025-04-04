package com.routebox.routebox.domain.user_mobile

import com.routebox.routebox.infrastructure.user_mobile.UserMobileRepository
import org.springframework.stereotype.Service

@Service
class UserMobileService(private val userMobileRepository: UserMobileRepository) {
    fun get(userId: Long): UserMobile? {
        return userMobileRepository.findByUserId(userId)
    }

    fun create(userMobile: UserMobile): Long {
        return userMobileRepository.save(userMobile).id
    }
}
