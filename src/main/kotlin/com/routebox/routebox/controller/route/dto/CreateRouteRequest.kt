package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.CreateRouteCommand
import com.routebox.routebox.util.DateTimeUtil
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat

data class CreateRouteRequest(
    @Schema(description = "루트 기록 시작일시", example = "2024-08-01T00:00:00")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val startTime: String,
    @Schema(description = "루트 기록 종료일시", example = "2024-08-01T00:00:00")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val endTime: String,
) {
    fun toCommand(userId: Long): CreateRouteCommand =
        CreateRouteCommand(
            userId = userId,
            startTime = DateTimeUtil.parseToLocalDateTime(startTime),
            endTime = DateTimeUtil.parseToLocalDateTime(endTime),
        )
}
