package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportRouteRequest(
    @Schema(description = "신고할 루트 id", example = "1")
    val routeId: Long,

    @Schema(description = "신고 내용", example = "욕설이 포함되어 있습니다.")
    val content: String?,
)
