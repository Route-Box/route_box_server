package com.routebox.routebox.application.user_report

import com.routebox.routebox.application.user_report.dto.ReportUserCommand
import com.routebox.routebox.domain.user_report.UserReportService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ReportUserUseCase(
    private val userReportService: UserReportService,
) {
    @Transactional
    operator fun invoke(command: ReportUserCommand): Long {
        val report = userReportService.report(
            reporterId = command.reporterId,
            reportedUserId = command.reportedUserId,
        )
        return report.id
    }
}
