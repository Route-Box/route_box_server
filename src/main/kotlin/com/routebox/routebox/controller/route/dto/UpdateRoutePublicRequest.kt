package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.UpdateRoutePublicCommand
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateRoutePublicRequest(
    @Schema(description = "공개 여부", example = "true")
    val isPublic: Boolean,
) {
    fun toCommand(userId: Long, routeId: Long): UpdateRoutePublicCommand {
        return UpdateRoutePublicCommand(
            userId = userId,
            routeId = routeId,
            isPublic = isPublic,
        )
    }
}
