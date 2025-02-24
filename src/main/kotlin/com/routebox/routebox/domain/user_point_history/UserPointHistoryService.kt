package com.routebox.routebox.domain.user_point_history

import com.routebox.routebox.infrastructure.user_point_history.UserPointHistoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPointHistoryService(private val userPointHistoryRepository: UserPointHistoryRepository) {
    @Transactional
    fun create(userPointHistory: UserPointHistory) {
        userPointHistoryRepository.save(userPointHistory)
    }

    @Transactional(readOnly = true)
    fun findByUserId(userId: Long, page: Int, pageSize: Int): Page<UserPointHistory> = userPointHistoryRepository.findByUserId(userId, PageRequest.of(page, pageSize))
}
