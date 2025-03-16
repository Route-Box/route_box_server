package com.routebox.routebox.controller.point.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class AddUserPointResponse(
    @Schema(description = "현재 포인트", example = "1000")
    @field:NotNull
    @field:Min(value = 1)
    val point: Int,
)
