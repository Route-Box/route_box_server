package com.routebox.routebox.application.route.dto

import java.time.LocalDateTime

data class CreateRouteCommand(
    val userId: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
)
