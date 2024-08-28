package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportUserRequest(
    @Schema(description = "신고할 사용자 id", example = "1")
    val reportedUserId: Long,
)
