package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.ActivityImageDto
import com.routebox.routebox.application.route.dto.ActivityResult
import io.swagger.v3.oas.annotations.media.Schema

data class RouteActivityResponse(
    @Schema(description = "활동 id")
    val activityId: Long,
    @Schema(description = "위치 이름")
    val locationName: String,
    @Schema(description = "위치 주소")
    val address: String,
    @Schema(description = "위도")
    val latitude: String?,
    @Schema(description = "경도")
    val longitude: String?,
    @Schema(description = "방문 날짜")
    val visitDate: String,
    @Schema(description = "방문 시작 시간")
    val startTime: String,
    @Schema(description = "방문 종료 시간")
    val endTime: String,
    @Schema(description = "카테고리")
    val category: String,
    @Schema(description = "설명")
    val description: String?,
    @Schema(description = "활동 이미지 url 목록")
    val activityImages: List<ActivityImageDto>,
) {
    companion object {
        fun from(routeActivityResult: ActivityResult) = RouteActivityResponse(
            activityId = routeActivityResult.activityId,
            locationName = routeActivityResult.locationName,
            address = routeActivityResult.address,
            latitude = routeActivityResult.latitude,
            longitude = routeActivityResult.longitude,
            visitDate = routeActivityResult.visitDate,
            startTime = routeActivityResult.startTime,
            endTime = routeActivityResult.endTime,
            category = routeActivityResult.category,
            description = routeActivityResult.description,
            activityImages = routeActivityResult.activityImageUrls,
        )
    }
}
