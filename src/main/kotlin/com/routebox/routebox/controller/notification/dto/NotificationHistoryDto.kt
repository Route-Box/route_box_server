package com.routebox.routebox.controller.notification.dto

import io.swagger.v3.oas.annotations.media.Schema

data class NotificationHistoryDto(
    @Schema(description = "알림 ID")
    val id: Long,

    @Schema(description = "알림 내용")
    val content: String,

    @Schema(description = "알림 생성일자 (yyyy-MM-dd)")
    val date: String,

    @Schema(description = "알림 읽음 여부")
    val isRead: Boolean,
) {
    companion object {
        fun from(id: Long, content: String, date: String, isRead: Boolean): NotificationHistoryDto = NotificationHistoryDto(
            id = id,
            content = content,
            date = date,
            isRead = isRead,
        )
    }
}
