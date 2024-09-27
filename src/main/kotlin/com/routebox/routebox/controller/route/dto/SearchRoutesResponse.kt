package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SearchRoutesResponse(
    @Schema(description = "검색된 루트 리스트")
    val routes: List<SearchRouteDto>,
) {
    companion object {
        fun from(routes: List<SearchRouteDto>): SearchRoutesResponse = SearchRoutesResponse(
            routes = routes,
        )
    }
}
