package com.routebox.routebox.controller.route.dto.home

import io.swagger.v3.oas.annotations.media.Schema

data class GetRecommendedRoutesResponse(
    @Schema(description = "오늘의 추천루트 문구")
    val comment: String?,

    @Schema(description = "추천 루트 목록")
    val routes: List<RecommendedRouteDto>,
) {
    companion object {
        fun from(comment: String?, routes: List<RecommendedRouteDto>): GetRecommendedRoutesResponse = GetRecommendedRoutesResponse(
            comment = comment,
            routes = routes,
        )
    }
}
