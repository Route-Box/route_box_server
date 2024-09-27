package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.RoutePoint

data class CreateRoutePointResult(
    val pointId: Long,
) {
    companion object {
        fun from(point: RoutePoint): CreateRoutePointResult = CreateRoutePointResult(
            pointId = point.id,
        )
    }
}
