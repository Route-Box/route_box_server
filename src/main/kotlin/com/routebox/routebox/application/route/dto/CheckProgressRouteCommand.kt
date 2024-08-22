package com.routebox.routebox.application.route.dto

import java.time.LocalDateTime

data class CheckProgressRouteCommand(
    val userId: Long,
    val userLocalTime: LocalDateTime,
)
