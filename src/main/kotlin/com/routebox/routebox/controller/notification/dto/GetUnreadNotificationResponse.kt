package com.routebox.routebox.controller.notification.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetUnreadNotificationResponse(
    @Schema(description = "읽지 않은 알림 존재 여부")
    val hasUnreadNotification: Boolean,
) {
    companion object {
        fun from(hasUnreadNotification: Boolean): GetUnreadNotificationResponse = GetUnreadNotificationResponse(
            hasUnreadNotification = hasUnreadNotification,
        )
    }
}
