package com.routebox.routebox.infrastructure.user_report

import com.routebox.routebox.domain.user_report.UserReport
import org.springframework.data.jpa.repository.JpaRepository

interface UserReportRepository : JpaRepository<UserReport, Long>
