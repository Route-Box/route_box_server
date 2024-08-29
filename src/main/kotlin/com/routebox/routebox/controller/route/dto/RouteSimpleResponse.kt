package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.GetMyRouteResult
import io.swagger.v3.oas.annotations.media.Schema

data class RouteSimpleResponse(
    @Schema(description = "루트 ID")
    val routeId: Long,
    @Schema(description = "루트 이름")
    val routeName: String?,
    @Schema(description = "루트 설명")
    val routeDescription: String?,
    @Schema(description = "루트 대표 이미지")
    val routeImageUrl: String?,
    @Schema(description = "루트 공개 여부")
    val isPublic: Boolean,
    @Schema(description = "구매 수")
    val purchaseCount: Int,
    @Schema(description = "댓글 수")
    val commentCount: Int,
    @Schema(description = "루트 작성 완료일", example = "2021-08-01T00:00:00")
    val createdAt: String?,
) {
    companion object {
        fun from(
            route: GetMyRouteResult,
        ) =
            RouteSimpleResponse(
                routeId = route.routeId,
                routeName = route.routeName,
                routeDescription = route.routeDescription,
                routeImageUrl = route.routeImageUrls.firstOrNull(),
                isPublic = route.isPublic,
                purchaseCount = route.purchaseCount,
                commentCount = route.commentCount,
                createdAt = route.recordFinishedAt?.toString(),
            )
    }
}
