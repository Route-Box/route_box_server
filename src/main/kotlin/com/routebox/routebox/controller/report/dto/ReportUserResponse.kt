package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportUserResponse(
    @Schema(description = "유저 신고 id")
    val userReportId: Long,
)
