package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.CreateRoutePointCommand
import io.swagger.v3.oas.annotations.media.Schema

data class CreateRoutePointRequest(
    @Schema(description = "위도", example = "37.123456")
    val latitude: String,
    @Schema(description = "경도", example = "127.123456")
    val longitude: String,
    @Schema(description = "경로 순서, 1부터 시작, 위치 기록할 때마다 +1 해서 전송", example = "1")
    val pointOrder: Int,
) {
    fun toCommand(userId: Long, routeId: Long): CreateRoutePointCommand =
        CreateRoutePointCommand(
            userId = userId,
            routeId = routeId,
            latitude = latitude,
            longitude = longitude,
            pointOrder = pointOrder,
        )
}
