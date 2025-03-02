package com.routebox.routebox.application.comment_report

import com.routebox.routebox.application.comment_report.dto.ReportCommentDto
import com.routebox.routebox.domain.comment_report.CommentReportService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ReportCommentUseCase(
    private val commentReportService: CommentReportService,
) {
    /*댓글 신고*/
    @Transactional
    operator fun invoke(dto: ReportCommentDto) = commentReportService.report(dto.reporterId, dto.reportedCommentId)
}
