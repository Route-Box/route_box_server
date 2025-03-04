package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.GetRouteInsightResult
import io.swagger.v3.oas.annotations.media.Schema

data class GetMyRouteInsightResponse(
    @Schema(description = "내 루트 수")
    val routeCount: Int,
    @Schema(description = "내가 작성한 루트의 총 구매 수")
    val purchaseCount: Int,
    @Schema(description = "내가 작성한 루트의 총 댓글 수")
    val commentCount: Int,
) {
    companion object {
        fun from(result: GetRouteInsightResult): GetMyRouteInsightResponse = GetMyRouteInsightResponse(
            routeCount = result.routeCount,
            purchaseCount = result.purchaseRouteCount,
            commentCount = result.commentCount,
        )
    }
}
