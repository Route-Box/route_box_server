package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetRecommendedRoutesResponse(
    @Schema(description = "오늘의 추천루트 문구")
    val comment: String,

    @Schema(description = "추천 루트 목록")
    val routes: List<RecommendRouteDto>,
) {
    companion object {
        fun from(comment: String, routes: List<RecommendRouteDto>): GetRecommendedRoutesResponse = GetRecommendedRoutesResponse(
            comment = comment,
            routes = routes,
        )
    }
}
