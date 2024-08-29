package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.Route
import java.time.LocalDateTime

data class FinishRecordRouteResult(
    val routeId: Long,
    val name: String,
    val description: String?,
    val recordFinishedAt: LocalDateTime,
) {
    companion object {
        fun from(route: Route): FinishRecordRouteResult {
            return FinishRecordRouteResult(
                routeId = route.id,
                name = route.name!!,
                description = route.description,
                recordFinishedAt = route.recordFinishedAt!!,
            )
        }
    }
}
