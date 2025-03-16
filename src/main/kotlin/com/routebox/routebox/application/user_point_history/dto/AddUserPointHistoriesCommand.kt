package com.routebox.routebox.application.user_point_history.dto

import com.routebox.routebox.domain.user.constant.UserPointTransactionType

data class AddUserPointHistoriesCommand(
    val userId: Long,
    val point: Int,
    val transactionType: UserPointTransactionType,
)
