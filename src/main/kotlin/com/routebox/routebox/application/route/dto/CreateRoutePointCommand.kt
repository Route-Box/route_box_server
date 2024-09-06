package com.routebox.routebox.application.route.dto

import java.time.LocalDateTime

data class CreateRoutePointCommand(
    val userId: Long,
    val routeId: Long,
    val latitude: String,
    val longitude: String,
    val recordAt: LocalDateTime,
)
