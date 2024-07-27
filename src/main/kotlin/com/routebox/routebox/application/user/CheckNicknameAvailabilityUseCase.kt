package com.routebox.routebox.application.user

import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CheckNicknameAvailabilityUseCase(private val userService: UserService) {
    @Transactional(readOnly = true)
    operator fun invoke(nickname: String): Boolean =
        userService.isNicknameAvailable(nickname)
}
