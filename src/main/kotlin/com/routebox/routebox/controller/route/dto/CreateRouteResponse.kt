package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CreateRouteResponse(
    @Schema(description = "루트 id", example = "1")
    val routeId: Long,
) {
    companion object {
        fun from(routeId: Long): CreateRouteResponse = CreateRouteResponse(
            routeId = routeId,
        )
    }
}
