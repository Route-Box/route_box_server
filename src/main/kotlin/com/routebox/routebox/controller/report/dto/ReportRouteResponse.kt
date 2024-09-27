package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportRouteResponse(
    @Schema(description = "루트 신고 id")
    val routeReportId: Long,
)
