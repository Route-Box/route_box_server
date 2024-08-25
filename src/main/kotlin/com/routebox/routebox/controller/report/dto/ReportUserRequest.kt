package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportUserRequest(
    @Schema(description = "신고할 사용자 id", example = "1")
    val userId: Long,

    @Schema(description = "신고 내용", example = "욕설이 포함되어 있습니다.")
    val content: String?,
)
