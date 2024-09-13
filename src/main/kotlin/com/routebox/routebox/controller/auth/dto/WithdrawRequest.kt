package com.routebox.routebox.controller.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

data class WithdrawRequest(
    @Schema(description = "탈퇴 사유", example = "기타")
    val reasonType: String,
    @Schema(description = "탈퇴 사유 상세", example = "기타")
    val reasonDetail: String?,
)
