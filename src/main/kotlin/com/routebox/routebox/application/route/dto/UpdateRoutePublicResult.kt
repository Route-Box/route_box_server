package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.Route

data class UpdateRoutePublicResult(
    val routeId: Long,
    val isPublic: Boolean,
) {
    companion object {
        fun from(route: Route): UpdateRoutePublicResult {
            return UpdateRoutePublicResult(
                routeId = route.id,
                isPublic = route.isPublic,
            )
        }
    }
}
