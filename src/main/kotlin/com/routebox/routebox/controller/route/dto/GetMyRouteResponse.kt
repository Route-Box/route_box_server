package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetMyRouteResponse(
    @Schema(description = "내루트 목록")
    val result: List<RouteSimpleResponse>,
) {
    companion object {
        fun from(
            result: List<RouteSimpleResponse>,
        ): GetMyRouteResponse {
            return GetMyRouteResponse(
                result = result,
            )
        }
    }
}
