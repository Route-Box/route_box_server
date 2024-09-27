package com.routebox.routebox.domain.purchased_route

import com.routebox.routebox.domain.route.RoutePoint
import java.time.LocalDateTime

data class PurchasedRoutePoint(
    val routePointId: Long,
    val latitude: String,
    val longitude: String,
    val recordAt: LocalDateTime,
) {
    companion object {
        fun from(routePoint: RoutePoint): PurchasedRoutePoint = PurchasedRoutePoint(
            routePointId = routePoint.id,
            latitude = routePoint.latitude,
            longitude = routePoint.longitude,
            recordAt = routePoint.recordAt,
        )
    }
}
