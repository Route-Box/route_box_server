package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateRoutePointResponse(
    @Schema(description = "위치 점 id", example = "1")
    val pointId: Long,
) {
    companion object {
        fun from(pointId: Long): CreateRoutePointResponse = CreateRoutePointResponse(
            pointId = pointId,
        )
    }
}
