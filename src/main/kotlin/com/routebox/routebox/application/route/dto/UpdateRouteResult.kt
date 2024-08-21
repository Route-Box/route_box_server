package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.Route

data class UpdateRouteResult(
    val routeId: Long,
    val name: String?,
    val description: String?,
    val whoWith: String?,
    val numberOfPeople: Int?,
    val numberOfDays: String?,
    val style: Array<String>,
    val transportation: String?,
) {
    companion object {
        fun from(route: Route): UpdateRouteResult {
            return UpdateRouteResult(
                routeId = route.id,
                name = route.name,
                description = route.description,
                whoWith = route.whoWith,
                numberOfPeople = route.numberOfPeople,
                numberOfDays = route.numberOfDays,
                style = route.style,
                transportation = route.transportations,
            )
        }
    }
}
