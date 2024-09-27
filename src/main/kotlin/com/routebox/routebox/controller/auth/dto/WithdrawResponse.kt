package com.routebox.routebox.controller.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

data class WithdrawResponse(
    @Schema(description = "사용자 ID")
    val userId: Long,
)
