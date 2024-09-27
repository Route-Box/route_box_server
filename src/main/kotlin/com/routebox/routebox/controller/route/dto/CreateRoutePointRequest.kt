package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.CreateRoutePointCommand
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class CreateRoutePointRequest(
    @Schema(description = "위도", example = "37.123456")
    val latitude: String,
    @Schema(description = "경도", example = "127.123456")
    val longitude: String,
    @Schema(description = "API 호출 시간", example = "2021-08-01T00:00:00")
    val recordAt: LocalDateTime,
) {
    fun toCommand(userId: Long, routeId: Long): CreateRoutePointCommand =
        CreateRoutePointCommand(
            userId = userId,
            routeId = routeId,
            latitude = latitude,
            longitude = longitude,
            recordAt = recordAt,
        )
}
