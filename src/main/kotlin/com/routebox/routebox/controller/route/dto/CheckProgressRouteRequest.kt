package com.routebox.routebox.controller.route.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class CheckProgressRouteRequest(
    @Schema(description = "사용자의 현재 날짜, 시간", example = "2021-08-01T12:00:00")
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val userLocalTime: String = LocalDateTime.now().toString(),
)
