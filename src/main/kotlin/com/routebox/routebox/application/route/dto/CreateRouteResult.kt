package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.Route

data class CreateRouteResult(
    val routeId: Long,
) {
    companion object {
        fun from(route: Route): CreateRouteResult = CreateRouteResult(
            routeId = route.id,
        )
    }
}
