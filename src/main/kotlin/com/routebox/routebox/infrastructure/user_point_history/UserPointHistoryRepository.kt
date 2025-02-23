package com.routebox.routebox.infrastructure.user_point_history

import com.routebox.routebox.domain.user_point_history.UserPointHistory
import org.springframework.data.jpa.repository.JpaRepository

interface UserPointHistoryRepository : JpaRepository<UserPointHistory, Long>
