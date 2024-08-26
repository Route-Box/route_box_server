package com.routebox.routebox.controller.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PurchasedRouteResponse(
    @Schema(description = "루트 ID")
    val routeId: Long,
    @Schema(description = "루트 이름")
    val routeName: String?,
    @Schema(description = "루트 설명")
    val routeDescription: String?,
    @Schema(description = "루트 대표 이미지")
    val routeImageUrl: String?,
    @Schema(description = "루트 생성일", example = "2024-08-01")
    val createdAt: String,
)
