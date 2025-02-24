package com.routebox.routebox.application.user

import com.routebox.routebox.controller.user.dto.UserResponse
import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetUserUseCase(private val userService: UserService) {
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): UserResponse {
        val user = userService.getUserById(userId)
        return UserResponse.from(user)
    }
}
