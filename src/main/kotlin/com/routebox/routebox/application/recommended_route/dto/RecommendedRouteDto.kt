package com.routebox.routebox.application.recommended_route.dto
data class RecommendedRouteDto(
    val id: Long,
    val name: String,
    val description: String?,
    val commonComment: String?,
    val routeImageUrl: String?,
)
