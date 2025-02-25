package com.routebox.routebox.application.comment.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetAllCommentsOfPostDto(
    @Schema(name = "댓글 id")
    val commentId: Long,

    @Schema(name = "댓글 작성자의 이름")
    val userNickname: String,

    @Schema(name = "댓글 작성자의 프로필 이미지 url", example = "https://www.pngkey.com/png/detail/99-999572_clipart-stock-woman-user-avatar-svg-png-icon.png")
    val userProfileImageUrl: String,

    @Schema(name = "댓글 내용", example = "너무 좋은 루트같아요!")
    val content: String,

    @Schema(name = "작성일 상대시간", description = "오늘을 기준으로 작성일이 며칠 전(몇 주 전)인지 나타냅니다.", example = "2일 전")
    val timeAgo: String,
)
