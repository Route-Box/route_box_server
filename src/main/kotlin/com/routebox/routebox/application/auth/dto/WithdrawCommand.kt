package com.routebox.routebox.application.auth.dto

import com.routebox.routebox.domain.auth.WithdrawalReasonType

data class WithdrawCommand(
    val userId: Long,
    val reasonType: WithdrawalReasonType,
    val reasonDetail: String?,
)
