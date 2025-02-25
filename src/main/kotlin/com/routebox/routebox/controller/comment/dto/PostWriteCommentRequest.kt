package com.routebox.routebox.controller.comment.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PostWriteCommentRequest(
    @Schema(description = "댓글 내용", example = "좋은 루트네요!!")
    @field:NotNull
    @field:NotBlank
    @field:Size(min = 1, max = 500)
    val content: String,
)
