package com.routebox.routebox.application.route_report.dto

import com.routebox.routebox.domain.route_report.constant.RouteReportReasonType

data class ReportRouteCommand(
    val reporterId: Long,
    val reportedRouteId: Long,
    val reasonType: RouteReportReasonType?,

    val reasonDetail: String?,
)
