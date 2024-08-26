package com.routebox.routebox.application.user.dto

import com.routebox.routebox.domain.route.Route

data class GetUserRouteResult(
    val routeId: Long,
    val routeName: String?,
    val routeDescription: String?,
    val routeImageUrl: String?,
    val createdAt: String,
) {
    companion object {
        fun from(route: Route) = GetUserRouteResult(
            routeId = route.id,
            routeName = route.name,
            routeDescription = route.description,
            routeImageUrl = route.routeActivities.map { a -> a.activityImages.first().fileUrl }.firstOrNull(),
            createdAt = route.createdAt.toString(),
        )
    }
}
