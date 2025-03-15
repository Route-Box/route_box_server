package com.routebox.routebox.controller.comment.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PatchModifyCommentResponse(
    @Schema(description = "요청 성공 여부: true(성공), false(실패)", example = "true")
    val isSuccess: Boolean,
)
