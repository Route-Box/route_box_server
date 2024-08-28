package com.routebox.routebox.domain.route_report

import com.routebox.routebox.domain.route_report.constant.RouteReportReasonType
import com.routebox.routebox.infrastructure.route_report.RouteReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RouteReportService(
    private val routeReportRepository: RouteReportRepository,
) {
    @Transactional
    fun report(
        reporterId: Long,
        reportedRouteId: Long,
        reasonType: RouteReportReasonType,
        reasonDetail: String?,
    ): RouteReport =
        routeReportRepository.save(
            RouteReport(
                reporterId = reporterId,
                reportedRouteId = reportedRouteId,
                reasonType = reasonType,
                reasonDetail = reasonDetail,
            ),
        )
}
