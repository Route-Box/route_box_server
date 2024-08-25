package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportResponse(
    @Schema(description = "신고 id")
    val reportId: Long,
)
