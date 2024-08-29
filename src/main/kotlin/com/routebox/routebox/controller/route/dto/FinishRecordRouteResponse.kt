package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class FinishRecordRouteResponse(
    @Schema(description = "루트 ID", example = "1")
    val routeId: Long,
    @Schema(description = "루트 제목", example = "루트 제목")
    val routeName: String,
    @Schema(description = "루트 설명", example = "루트 설명")
    val routeDescription: String?,
    @Schema(description = "루트 생성일시", example = "2024-08-01T00:00:00")
    val recordFinishedAt: String,
) {
    companion object {
        fun from(routeId: Long, name: String, description: String?, recordFinishedAt: LocalDateTime): FinishRecordRouteResponse {
            return FinishRecordRouteResponse(
                routeId = routeId,
                routeName = name,
                routeDescription = description,
                recordFinishedAt = recordFinishedAt.toString(),
            )
        }
    }
}
