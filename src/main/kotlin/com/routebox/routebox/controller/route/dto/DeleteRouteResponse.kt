package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.DeleteRouteResult
import io.swagger.v3.oas.annotations.media.Schema

data class DeleteRouteResponse(
    @Schema(description = "삭제된 루트 ID")
    val routeId: Long,
) {
    companion object {
        fun from(deleteRouteResult: DeleteRouteResult): DeleteRouteResponse {
            return DeleteRouteResponse(deleteRouteResult.routeId)
        }
    }
}
