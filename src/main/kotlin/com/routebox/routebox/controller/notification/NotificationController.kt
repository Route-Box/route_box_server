package com.routebox.routebox.controller.notification

import com.routebox.routebox.controller.notification.dto.GetNotificationHistoryResponse
import com.routebox.routebox.controller.notification.dto.GetUnreadNotificationResponse
import com.routebox.routebox.controller.notification.dto.NotificationHistoryDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@Tag(name = "알림 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class NotificationController {
    @Operation(
        summary = "알림 내역 조회",
        description = "알림 내역 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @GetMapping("/v1/notifications")
    fun getNotificationHistory(): GetNotificationHistoryResponse {
        val mockData = listOf(
            NotificationHistoryDto.from(1, "알림 내용1", "2024-08-04", false),
            NotificationHistoryDto.from(2, "알림 내용2", "2024-08-03", false),
            NotificationHistoryDto.from(3, "알림 내용3", "2024-08-02", true),
            NotificationHistoryDto.from(4, "알림 내용4", "2024-08-02", true),
            NotificationHistoryDto.from(5, "알림 내용5", "2024-08-01", true),
            NotificationHistoryDto.from(6, "알림 내용6", "2024-08-01", true),
            NotificationHistoryDto.from(7, "알림 내용7", "2024-08-01", true),
            NotificationHistoryDto.from(8, "알림 내용8", "2024-07-31", true),
            NotificationHistoryDto.from(9, "알림 내용9", "2024-07-30", true),
            NotificationHistoryDto.from(10, "알림 내용10", "2024-07-20", true),
        )
        return GetNotificationHistoryResponse.from(mockData)
    }

    @Operation(
        summary = "안읽은 알림 여부 조회",
        description = "안읽은 알림 여부 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @GetMapping("/v1/notifications/unread")
    fun checkUnreadNotifications(): GetUnreadNotificationResponse {
        val mockData: Boolean = Random.nextBoolean()
        return GetUnreadNotificationResponse.from(mockData)
    }
}
