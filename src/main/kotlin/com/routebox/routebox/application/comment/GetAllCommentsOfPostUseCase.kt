package com.routebox.routebox.application.comment

import com.routebox.routebox.application.comment.dto.GetAllCommentsOfPostDto
import com.routebox.routebox.domain.comment.CommentService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetAllCommentsOfPostUseCase(
    private val commentService: CommentService,
) {
    /* 게시글의 모든 댓글 조회 */
    @Transactional(readOnly = true)
    operator fun invoke(routeId: Long): List<GetAllCommentsOfPostDto> = commentService.getAllCommentsOfPost(routeId)
}
