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
        val mockData: List<NotificationHistoryDto>  = listOf()
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
