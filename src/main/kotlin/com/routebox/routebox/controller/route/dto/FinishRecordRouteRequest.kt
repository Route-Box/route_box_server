package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.FinishRecordRouteCommand
import io.swagger.v3.oas.annotations.media.Schema

data class FinishRecordRouteRequest(
    @Schema(description = "루트 제목", example = "루트 제목")
    val routeName: String,
    @Schema(description = "루트 설명", example = "루트 설명")
    val routeDescription: String?,
) {
    fun toCommand(routeId: Long): FinishRecordRouteCommand {
        return FinishRecordRouteCommand(
            routeId = routeId,
            name = routeName,
            description = routeDescription,
        )
    }
}
