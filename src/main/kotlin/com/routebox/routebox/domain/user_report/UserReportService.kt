package com.routebox.routebox.domain.user_report

import com.routebox.routebox.infrastructure.user_report.UserReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserReportService(
    private val userReportRepository: UserReportRepository,
) {
    @Transactional
    fun report(reporterId: Long, reportedUserId: Long): UserReport =
        userReportRepository.save(
            UserReport(
                reporterId = reporterId,
                reportedUserId = reportedUserId,
            ),
        )
}
