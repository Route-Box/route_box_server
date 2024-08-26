package com.routebox.routebox.controller.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetUserRoutesResponse(
    @Schema(description = "유저 루트 목록")
    val routes: List<UserRouteResponse>,
) {
    companion object {
        fun from(result: List<UserRouteResponse>) = GetUserRoutesResponse(
            routes = result,
        )
    }
}
