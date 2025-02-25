package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.CreateRoutePointCommand
import io.swagger.v3.oas.annotations.media.Schema

data class CreateRoutePointsRequest(
    @Schema
    val points: List<RoutePointDto>,
) {
    fun toCommand(userId: Long, routeId: Long): List<CreateRoutePointCommand> =
        points.map { point ->
            CreateRoutePointCommand(
                userId = userId,
                routeId = routeId,
                latitude = point.latitude,
                longitude = point.longitude,
                recordAt = point.recordAt,
            )
        }
}
