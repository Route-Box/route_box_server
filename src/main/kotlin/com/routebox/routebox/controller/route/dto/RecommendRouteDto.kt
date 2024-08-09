package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class RecommendRouteDto(
    @Schema(description = "루트 ID")
    val id: Long,

    @Schema(description = "루트 이름")
    val routeName: String,

    @Schema(description = "루트 설명")
    val routeDescription: String,

    @Schema(description = "루트 대표 이미지")
    val routeImageUrl: String,
) {
    companion object {
        fun from(id: Long, routeName: String, routeDescription: String, routeImageUrl: String): RecommendRouteDto = RecommendRouteDto(
            id = id,
            routeName = routeName,
            routeDescription = routeDescription,
            routeImageUrl = routeImageUrl,
        )
    }
}
