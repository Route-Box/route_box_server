package com.routebox.routebox.infrastructure.user_point_history

import com.routebox.routebox.domain.user_point_history.UserPointHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointHistoryRepository : JpaRepository<UserPointHistory, Long> {
    fun findByUserId(userId: Long, pageable: Pageable): Page<UserPointHistory>
}
