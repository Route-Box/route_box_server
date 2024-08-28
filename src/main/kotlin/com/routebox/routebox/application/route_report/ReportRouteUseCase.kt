package com.routebox.routebox.application.route_report

import com.routebox.routebox.application.route_report.dto.ReportRouteCommand
import com.routebox.routebox.domain.route_report.RouteReportService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ReportRouteUseCase(
    private val routeReportService: RouteReportService,
    // TODO: 차단 기능 추가.
) {
    @Transactional
    operator fun invoke(command: ReportRouteCommand): Long {
        val report = routeReportService.report(
            reporterId = command.reporterId,
            reportedRouteId = command.reportedRouteId,
            reasonType = command.reasonType,
            reasonDetail = command.reasonDetail,
        )
        return report.id
    }
}
