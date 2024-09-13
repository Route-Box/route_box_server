package com.routebox.routebox.infrastructure.auth

import com.routebox.routebox.domain.auth.WithdrawalHistory
import org.springframework.data.jpa.repository.JpaRepository

interface WithdrawalHistoryRepository : JpaRepository<WithdrawalHistory, Long>
