package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetLatestRoutesResponse(
    @Schema(description = "최신 루트 목록")
    val result: List<RouteResponse>,
) {
    companion object {
        fun from(routineResponse: List<RouteResponse>): GetLatestRoutesResponse = GetLatestRoutesResponse(
            result = routineResponse,
        )
    }
}
