package com.routebox.routebox.infrastructure.comment_report

import com.routebox.routebox.domain.comment_report.CommentReport
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint:standard:function-naming")
interface CommentReportRepository : JpaRepository<CommentReport, Long>
