package com.routebox.routebox.controller.user.dto

import com.routebox.routebox.application.user.dto.GetUserRouteResult
import io.swagger.v3.oas.annotations.media.Schema

data class UserRouteResponse(
    @Schema(description = "루트 ID", example = "1")
    val routeId: Long,
    @Schema(description = "루트 이름", example = "제주도 여행")
    val routeName: String?,
    @Schema(description = "루트 설명", example = "제주도 여행을 즐기자")
    val routeDescription: String?,
    @Schema(description = "대표 이미지", example = "https://routebox.s3.ap-northeast-2.amazonaws.com/1.jpg")
    val routeImageUrl: String?,
    @Schema(description = "구매 수", example = "10")
    val purchaseCount: Int,
    @Schema(description = "댓글 수", example = "5")
    val commentCount: Int,
    @Schema(description = "루트 생성일", example = "2021-08-01")
    val createdAt: String,
) {
    companion object {
        fun from(result: GetUserRouteResult) = UserRouteResponse(
            routeId = result.routeId,
            routeName = result.routeName,
            routeDescription = result.routeDescription,
            routeImageUrl = result.routeImageUrl,
            // TODO: 구매 수, 댓글 수
            purchaseCount = 0,
            commentCount = 0,
            createdAt = result.createdAt,
        )
    }
}
