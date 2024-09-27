package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.route.Route
import java.time.LocalDateTime

data class GetRouteDetailResult(
    val routeId: Long,
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val routeName: String?,
    val routeDescription: String?,
    val routeImageUrls: List<String>,
    val isPurchased: Boolean,
    val purchaseCount: Int,
    val commentCount: Int,
    val routeStyles: List<String>,
    val whoWith: String?,
    val transportation: String?,
    val numberOfPeople: Int?,
    val numberOfDays: String?,
    val recordFinishedAt: LocalDateTime?,
) {
    companion object {
        // TODO: 구매여부, 구매수, 댓글 수 - 실제 로직 적용 필요
        fun from(
            route: Route,
        ): GetRouteDetailResult = GetRouteDetailResult(
            routeId = route.id,
            userId = route.user.id,
            nickname = route.user.nickname,
            profileImageUrl = route.user.profileImageUrl,
            routeName = route.name,
            routeDescription = route.description,
            routeImageUrls = route.routeActivities.map { r -> r.activityImages.map { image -> image.fileUrl } }
                .flatten(),
            isPurchased = false,
            purchaseCount = 0,
            commentCount = 0,
            routeStyles = route.style.toList(),
            whoWith = route.whoWith,
            transportation = route.transportation,
            numberOfPeople = route.numberOfPeople,
            numberOfDays = route.numberOfDays,
            recordFinishedAt = route.recordFinishedAt,
        )
    }
}
