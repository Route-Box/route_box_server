package com.routebox.routebox.controller.purchased_route.dto

import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class FindPurchasedRoutesResponse {
    data class PurchasedRouteResponse(
        @Schema(description = "Id of purchased route")
        val id: Long,

        @Schema(description = "제목", example = "공주들이랑 함께한 경주 여행")
        val name: String?,

        @Schema(description = "설명", example = "이런저런 얘기들이 들어갑니다...")
        val description: String?,

        @Schema(description = "구매 시각")
        val purchasedAt: LocalDateTime,

        @Schema(
            description = "썸네일 이미지. 루트 활동에 설정된 이미지 중 하나로 설정되며, 루트 활동에 이미지가 없는 경우 <code>null</code>로 응답",
            example = "https://thumbnail-image-url",
        )
        val thumbnailImageUrl: String?,
    ) {
        companion object {
            fun from(purchasedRoute: PurchasedRoute): PurchasedRouteResponse =
                PurchasedRouteResponse(
                    id = purchasedRoute.id,
                    name = purchasedRoute.name,
                    description = purchasedRoute.description,
                    purchasedAt = purchasedRoute.createdAt,
                    thumbnailImageUrl = purchasedRoute.routeActivities
                        .firstOrNull { routeActivity -> routeActivity.activityImageUrls.isNotEmpty() }
                        ?.let { routeActivity -> routeActivity.activityImageUrls[0] },
                )
        }
    }
}
