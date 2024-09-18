package com.routebox.routebox.controller.route.dto.home

import io.swagger.v3.oas.annotations.media.Schema

data class RecommendedRouteDto(
    @Schema(description = "루트 ID")
    val id: Long,

    @Schema(description = "루트 이름")
    val routeName: String,

    @Schema(description = "루트 설명")
    val routeDescription: String?,

    @Schema(description = "루트 대표 이미지")
    val routeImageUrl: String?,
) {
    companion object {
        fun from(id: Long, routeName: String, routeDescription: String?, routeImageUrl: String?): RecommendedRouteDto = RecommendedRouteDto(
            id = id,
            routeName = routeName,
            routeDescription = routeDescription,
            routeImageUrl = if (routeImageUrl.isNullOrEmpty()) null else routeImageUrl,
        )
    }
}
