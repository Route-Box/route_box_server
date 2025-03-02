package com.routebox.routebox.application.comment

import com.routebox.routebox.domain.comment.CommentService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteCommentUseCase(
    private val commentService: CommentService,
) {
    /*댓글 삭제*/
    @Transactional
    operator fun invoke(id: Long) = commentService.deleteComment(id)
}
