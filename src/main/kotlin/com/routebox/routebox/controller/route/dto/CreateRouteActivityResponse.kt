package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.ActivityImageDto
import com.routebox.routebox.application.route.dto.CreateRouteActivityResult
import io.swagger.v3.oas.annotations.media.Schema

data class CreateRouteActivityResponse(
    @Schema(description = "활동 ID", example = "1")
    val activityId: Long,
    @Schema(description = "위치 이름", example = "강릉 해파랑물회")
    val locationName: String,
    @Schema(description = "위치 주소", example = "강원도 강릉시 강릉대로 365")
    val address: String,
    @Schema(description = "위치 위도", example = "37.751007")
    val latitude: String?,
    @Schema(description = "위치 경도", example = "128.876614")
    val longitude: String?,
    @Schema(description = "방문 날짜", example = "2021-08-01")
    val visitDate: String,
    @Schema(description = "방문 시작 시간", example = "10:00:00")
    val startTime: String,
    @Schema(description = "방문 종료 시간", example = "12:00:00")
    val endTime: String,
    @Schema(description = "카테고리", example = "식당")
    val category: String,
    @Schema(description = "설명", example = "해파랑물회는 강릉에서 유명한 음식점입니다.")
    val description: String?,
    @Schema(description = "활동 이미지 ID 및 URL 목록", example = "[{\"id\":1,\"url\":\"https://activity-image1\"}, {\"id\":2,\"url\":\"https://activity-image2\"}]")
    val activityImages: List<ActivityImageDto>,
) {
    companion object {
        fun from(
            result: CreateRouteActivityResult,
        ): CreateRouteActivityResponse = CreateRouteActivityResponse(
            activityId = result.activityId,
            locationName = result.locationName,
            address = result.address,
            latitude = result.latitude,
            longitude = result.longitude,
            visitDate = result.visitDate,
            startTime = result.startTime,
            endTime = result.endTime,
            category = result.category,
            description = result.description,
            activityImages = result.activityImages,
        )
    }
}
