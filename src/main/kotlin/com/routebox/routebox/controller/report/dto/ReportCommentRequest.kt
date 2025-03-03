package com.routebox.routebox.controller.report.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ReportCommentRequest(
    @Schema(description = "신고할 댓글 id", example = "1")
    val commentId: Long,
)
