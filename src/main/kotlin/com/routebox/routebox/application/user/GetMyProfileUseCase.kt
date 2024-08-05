package com.routebox.routebox.application.user

import com.routebox.routebox.application.user.dto.GetMyProfileResult
import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetMyProfileUseCase(private val userService: UserService) {

    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): GetMyProfileResult {
        val user = userService.getUserById(userId)
        return GetMyProfileResult.from(user)
    }
}
