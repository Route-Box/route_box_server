package com.routebox.routebox.controller.user_point_history.dto

import com.routebox.routebox.domain.route.Route
import com.routebox.routebox.domain.user.constant.UserPointTransactionType
import com.routebox.routebox.domain.user_point_history.UserPointHistory
import io.swagger.v3.oas.annotations.media.Schema

data class UserPointHistoryResponse(
    @Schema(description = "Id of point history")
    val id: Long,

    @Schema(description = "포인트를 사용/충전한 유저의 id")
    val userId: Long,

    @Schema(description = "(루트 구매, 루트 판매의 경우) 루트 정보")
    val route: RouteResponse?,

    @Schema(description = "포인트 거래 유형")
    val transactionType: UserPointTransactionType,

    @Schema(description = "거래한 포인트 양")
    val amount: Int,
) {
    companion object {
        fun from(userPointHistory: UserPointHistory, route: Route?) = UserPointHistoryResponse(
            id = userPointHistory.id,
            userId = userPointHistory.userId,
            route = route?.let { RouteResponse.from(it) },
            transactionType = userPointHistory.transactionType,
            amount = userPointHistory.amount,
        )
    }

    data class RouteResponse(
        @Schema(description = "Id of route")
        val id: Long,

        @Schema(description = "루트 이름", example = "제주 식도락 여행")
        val name: String?,

        @Schema(description = "루트 대표 이미지", example = "https://route-image-1")
        val thumbnailImageUrl: String?,
    ) {
        companion object {
            fun from(route: Route) = RouteResponse(
                id = route.id,
                name = route.name,
                thumbnailImageUrl = route
                    .routeActivities.firstOrNull()
                    ?.activityImages?.firstOrNull()
                    ?.fileUrl,
            )
        }
    }
}
