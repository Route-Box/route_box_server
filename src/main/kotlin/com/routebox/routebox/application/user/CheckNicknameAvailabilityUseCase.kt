package com.routebox.routebox.application.user

import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class CheckNicknameAvailabilityUseCase(private val userService: UserService) {
    operator fun invoke(nickname: String): Boolean =
        userService.isNicknameAvailable(nickname)
}
