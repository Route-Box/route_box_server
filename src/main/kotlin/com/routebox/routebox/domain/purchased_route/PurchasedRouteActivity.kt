package com.routebox.routebox.domain.purchased_route

import com.routebox.routebox.domain.route.RouteActivity
import java.time.LocalDate
import java.time.LocalTime

data class PurchasedRouteActivity(
    val routeActivityId: Long,
    val locationName: String,
    val address: String,
    val latitude: String?,
    val longitude: String?,
    val visitDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val category: String,
    val description: String?,
    val activityImageUrls: List<String>,
) {
    companion object {
        fun fromRouteActivity(routeActivity: RouteActivity): PurchasedRouteActivity = PurchasedRouteActivity(
            routeActivityId = routeActivity.id,
            locationName = routeActivity.locationName,
            address = routeActivity.address,
            latitude = routeActivity.latitude,
            longitude = routeActivity.longitude,
            visitDate = routeActivity.visitDate,
            startTime = routeActivity.startTime,
            endTime = routeActivity.endTime,
            category = routeActivity.category,
            description = routeActivity.description,
            activityImageUrls = routeActivity.activityImages.map { image -> image.fileUrl },
        )
    }
}
