package com.routebox.routebox.application.user

import com.routebox.routebox.application.user.dto.GetUserProfileResult
import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetUserProfileUseCase(private val userService: UserService) {

    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): GetUserProfileResult {
        val user = userService.getUserById(userId)
        return GetUserProfileResult.from(user)
    }
}
