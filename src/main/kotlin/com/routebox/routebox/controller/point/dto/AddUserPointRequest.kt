package com.routebox.routebox.controller.point.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class AddUserPointRequest(
    @Schema(description = "충전할 포인트 양", example = "1000")
    @field:NotNull
    @field:Min(value = 1)
    val point: Int,
)
