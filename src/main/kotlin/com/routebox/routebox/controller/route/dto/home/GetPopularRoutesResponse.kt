package com.routebox.routebox.controller.route.dto.home

import io.swagger.v3.oas.annotations.media.Schema

data class GetPopularRoutesResponse(
    @Schema(description = "인기 루트 목록")
    val routes: List<PopularRouteDto>,
) {
    companion object {
        fun from(routes: List<PopularRouteDto>): GetPopularRoutesResponse = GetPopularRoutesResponse(
            routes = routes,
        )
    }
}
