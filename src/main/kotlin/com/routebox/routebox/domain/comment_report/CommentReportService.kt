package com.routebox.routebox.domain.comment_report

import com.routebox.routebox.domain.comment.Comment
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.exception.comment.CommentNotFoundException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.infrastructure.comment.CommentRepository
import com.routebox.routebox.infrastructure.comment_report.CommentReportRepository
import com.routebox.routebox.infrastructure.user.UserRepository
import org.springframework.stereotype.Service

@Service
class CommentReportService(
    private val commentReportRepository: CommentReportRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
) {
    /*댓글 신고*/
    fun report(reporterId: Long, reportedCommentId: Long) {
        // id에 해당하는 각 객체 조회
        val user: User = userRepository.findById(reporterId)
            .orElseThrow { throw UserNotFoundException() }
        val comment: Comment = commentRepository.findById(reportedCommentId)
            .orElseThrow { throw CommentNotFoundException() }

        // 댓글 신고
        commentReportRepository.save(
            CommentReport(
                user,
                comment,
            ),
        )
    }
}
