package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.CreateRouteActivityCommand
import com.routebox.routebox.util.DateTimeUtil
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.web.multipart.MultipartFile

data class CreateRouteActivityRequest(
    @Schema(description = "위치 이름", example = "강릉 해파랑물회")
    @field:NotNull
    val locationName: String,
    @Schema(description = "위치 주소", example = "강원도 강릉시 강릉대로 365")
    @field:NotNull
    val address: String,
    @Schema(description = "위치 위도", example = "37.751007", required = false)
    val latitude: String?,
    @Schema(description = "위치 경도", example = "128.876614", required = false)
    val longitude: String?,
    @Schema(description = "방문 날짜", example = "2021-08-01")
    @field:NotNull
    val visitDate: String,
    @Schema(description = "방문 시작 시간", example = "10:00")
    @field:NotNull
    val startTime: String,
    @Schema(description = "방문 종료 시간", example = "12:00")
    @field:NotNull
    val endTime: String,
    @Schema(description = "카테고리", example = "식당")
    @field:NotNull
    val category: String,
    @Schema(description = "설명", example = "해파랑물회는 강릉에서 유명한 음식점입니다.")
    @field:Length(max = 40)
    val description: String?,
    @Schema(description = "루트 활동 이미지")
    var activityImages: List<MultipartFile>?,
) {
    fun toCommand(userId: Long, routeId: Long): CreateRouteActivityCommand {
        return CreateRouteActivityCommand(
            userId = userId,
            routeId = routeId,
            locationName = locationName,
            address = address,
            latitude = latitude,
            longitude = longitude,
            visitDate = DateTimeUtil.parseToLocalDate(visitDate),
            startTime = DateTimeUtil.parseToLocalTime(startTime),
            endTime = DateTimeUtil.parseToLocalTime(endTime),
            category = category,
            description = description,
            images = activityImages ?: emptyList(),
        )
    }
}
