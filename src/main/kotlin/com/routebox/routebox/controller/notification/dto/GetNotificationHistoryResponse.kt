package com.routebox.routebox.controller.notification.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetNotificationHistoryResponse(
    @Schema(description = "알림 목록")
    val notifications: List<NotificationHistoryDto>,
) {
    companion object {
        fun from(notifications: List<NotificationHistoryDto>): GetNotificationHistoryResponse = GetNotificationHistoryResponse(
            notifications = notifications,
        )
    }
}
