package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.Route

data class GetRouteDetailWithActivitiesResult(
    val routeId: Long,
    val userId: Long,
    val profileImageUrl: String,
    val nickname: String,
    val routeName: String?,
    val routeDescription: String?,
    val startTime: String,
    val endTime: String,
    val whoWith: String?,
    val routeStyles: List<String>,
    val numberOfPeople: Int?,
    val numberOfDays: String?,
    val transportation: String?,
    val isPublic: Boolean,
    val createdAt: String,
    val routePath: List<Map<String, String>>,
    val routeActivities: List<ActivityResult>,
) {
    companion object {
        fun from(
            route: Route,
        ): GetRouteDetailWithActivitiesResult = GetRouteDetailWithActivitiesResult(
            routeId = route.id,
            userId = route.user.id,
            profileImageUrl = route.user.profileImageUrl,
            nickname = route.user.nickname,
            routeName = route.name,
            routeDescription = route.description,
            startTime = route.startTime.toString(),
            endTime = route.endTime.toString(),
            whoWith = route.whoWith,
            routeStyles = route.style.toList(),
            numberOfPeople = route.numberOfPeople,
            numberOfDays = route.numberOfDays,
            transportation = route.transportation,
            isPublic = route.isPublic,
            createdAt = route.createdAt.toString(),
            routePath = route.routePoints.map { mapOf("latitude" to it.latitude, "longitude" to it.longitude) },
            routeActivities = route.routeActivities.map { ActivityResult.from(it) },
        )
    }
}
