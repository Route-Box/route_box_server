package com.routebox.routebox.domain.user_point_history

import com.routebox.routebox.infrastructure.user_point_history.UserPointHistoryRepository
import org.springframework.stereotype.Service

@Service
class UserPointHistoryService(private val userPointHistoryRepository: UserPointHistoryRepository) {
    fun create(userPointHistory: UserPointHistory) {
        userPointHistoryRepository.save(userPointHistory)
    }
}
