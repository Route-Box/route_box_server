package com.routebox.routebox.application.comment

import com.routebox.routebox.domain.comment.CommentService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ModifyCommentUseCase(
    private val commentService: CommentService,
) {
    /*댓글 내용 수정*/
    @Transactional
    operator fun invoke(id: Long, content: String, userId: Long) = commentService.modifyComment(id, content, userId)
}
