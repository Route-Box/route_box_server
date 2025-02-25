package com.routebox.routebox.application.comment

import com.routebox.routebox.domain.comment.CommentService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WriteCommentUseCase(
    private val commentService: CommentService,
) {
    /*댓글 작성*/
    @Transactional
    operator fun invoke(userId: Long, routeId: Long, content: String) = commentService.writeComment(userId, routeId, content)
}
