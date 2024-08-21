package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.DeleteRouteActivityResult
import io.swagger.v3.oas.annotations.media.Schema

data class DeleteRouteActivityResponse(
    @Schema(description = "삭제된 활동 ID")
    val activityId: Long,
) {
    companion object {
        fun from(deleteRouteActivityResult: DeleteRouteActivityResult): DeleteRouteActivityResponse {
            return DeleteRouteActivityResponse(deleteRouteActivityResult.activityId)
        }
    }
}
