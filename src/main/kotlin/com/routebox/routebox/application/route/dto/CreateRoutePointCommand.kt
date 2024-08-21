package com.routebox.routebox.application.route.dto

data class CreateRoutePointCommand(
    val userId: Long,
    val routeId: Long,
    val latitude: String,
    val longitude: String,
    val pointOrder: Int,
)
