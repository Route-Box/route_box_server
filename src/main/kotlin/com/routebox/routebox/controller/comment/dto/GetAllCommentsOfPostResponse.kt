package com.routebox.routebox.controller.comment.dto

import com.routebox.routebox.application.comment.dto.GetAllCommentsOfPostDto
import io.swagger.v3.oas.annotations.media.Schema

data class GetAllCommentsOfPostResponse(
    @Schema(name = "댓글 목록", description = "게시글에 속한 댓글 목록")
    val comments: List<GetAllCommentsOfPostDto>,
)
