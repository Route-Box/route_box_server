package com.routebox.routebox.controller.report.dto

import com.routebox.routebox.domain.route_report.constant.RouteReportReasonType
import io.swagger.v3.oas.annotations.media.Schema

data class ReportRouteRequest(
    @Schema(description = "신고할 루트 id", example = "1")
    val routeId: Long,

    @Schema(description = "신고 사유")
    val reasonTypes: List<RouteReportReasonType>?,

    // TODO: 내용 글자수 제한 확인 필요
    @Schema(description = "구체적인 신고 사유", example = "욕설이 포함되어 있습니다.")
    val reasonDetail: String?,
)
