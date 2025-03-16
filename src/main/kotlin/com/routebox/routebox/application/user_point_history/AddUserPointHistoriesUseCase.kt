package com.routebox.routebox.application.user_point_history

import com.routebox.routebox.application.user_point_history.dto.AddUserPointHistoriesCommand
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user_point_history.UserPointHistory
import com.routebox.routebox.domain.user_point_history.UserPointHistoryService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class AddUserPointHistoriesUseCase(
    private val userPointHistoryService: UserPointHistoryService,
    private val userService: UserService,
) {
    @Transactional
    operator fun invoke(command: AddUserPointHistoriesCommand): Int {
        val userPointHistory = UserPointHistory(
            userId = command.userId,
            amount = command.point,
            transactionType = command.transactionType,
            routeId = null,
        )

        // 포인트 이력 추가
        userPointHistoryService.create(userPointHistory)

        // 포인트 추가
        return userService.addPoint(command.userId, command.point)
    }
}
