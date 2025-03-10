package com.routebox.routebox.controller.comment.dto

import com.routebox.routebox.application.comment.dto.GetAllCommentsOfPostDto
import io.swagger.v3.oas.annotations.media.Schema

data class GetAllCommentsOfPostResponse(
    @Schema(description = "게시글의 댓글 목록")
    val comments: List<GetAllCommentsOfPostDto>,
)
