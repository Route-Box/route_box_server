package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.UpdateRoutePublicResult
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateRoutePublicResponse(
    @Schema(description = "루트 ID")
    val routeId: Long,
    @Schema(description = "공개 여부")
    val isPublic: Boolean,
) {
    companion object {
        fun from(result: UpdateRoutePublicResult): UpdateRoutePublicResponse {
            return UpdateRoutePublicResponse(
                routeId = result.routeId,
                isPublic = result.isPublic,
            )
        }
    }
}
