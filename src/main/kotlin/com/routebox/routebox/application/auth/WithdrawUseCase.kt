package com.routebox.routebox.application.auth

import com.routebox.routebox.application.auth.dto.WithdrawCommand
import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.user.UserService
import jakarta.validation.Valid
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WithdrawUseCase(
    private val userService: UserService,
    private val authService: AuthService,
) {
    /**
     * 회원 탈퇴
     * @param command
     */
    @Transactional
    operator fun invoke(@Valid command: WithdrawCommand) {
        val user = userService.getUserById(command.userId)
        authService.withdrawUser(user, command.reasonType, command.reasonDetail)
    }
}
