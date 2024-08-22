package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.RouteActivity

data class ActivityResult(
    val activityId: Long,
    val locationName: String,
    val address: String,
    val latitude: String?,
    val longitude: String?,
    val visitDate: String,
    val startTime: String,
    val endTime: String,
    val category: String,
    val description: String?,
    val activityImageUrls: List<ActivityImageDto>,
) {
    companion object {
        fun from(
            activity: RouteActivity,
        ): ActivityResult = ActivityResult(
            activityId = activity.id,
            locationName = activity.locationName,
            address = activity.address,
            latitude = activity.latitude,
            longitude = activity.longitude,
            visitDate = activity.visitDate.toString(),
            startTime = activity.startTime.toString(),
            endTime = activity.endTime.toString(),
            category = activity.category,
            description = activity.description,
            activityImageUrls = activity.activityImages.map { ActivityImageDto(it.id, it.fileUrl) },
        )
    }
}
