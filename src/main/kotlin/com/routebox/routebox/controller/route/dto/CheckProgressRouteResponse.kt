package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CheckProgressRouteResponse(
    @Schema(description = "루트 ID, 기록중인 루트가 있는 경우 id 반환. 없는 경우 null 반환")
    val routeId: Long?,
)
