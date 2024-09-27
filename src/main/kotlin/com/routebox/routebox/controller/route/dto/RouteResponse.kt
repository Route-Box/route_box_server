package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class RouteResponse(
    @Schema(description = "Id(PK) of route", example = "1")
    val routeId: Long,

    @Schema(description = "Id(PK) of user", example = "1")
    val userId: Long,

    @Schema(description = "유저 프로필 이미지(url)", example = "https://user-profile-image")
    val profileImageUrl: String,

    @Schema(description = "닉네임", example = "고작가")
    val nickname: String,

    @Schema(description = "루트 제목", example = "서울의 작가들")
    val routeName: String?,

    @Schema(description = "루트 설명", example = "서울의 작가들을 만나보세요.")
    val routeDescription: String?,

    @Schema(description = "루트 이미지들", example = "[\"https://route-image1\", \"https://route-image2\"]")
    val routeImageUrls: List<String>,

    @Schema(description = "루트 구매 여부", example = "false")
    val isPurchased: Boolean,

    @Schema(description = "루트 구매 수", example = "0")
    val purchaseCount: Int,

    @Schema(description = "루트 댓글 수", example = "0")
    val commentCount: Int,

    @Schema(description = "루트 스타일들", example = "[\"뚜벅뚜벅\"]")
    val routeStyles: List<String>,

    @Schema(description = "함께한 사람들", example = "혼자")
    val whoWith: String?,

    @Schema(description = "이동수단", example = "도보")
    val transportation: String?,

    @Schema(description = "인원 수", example = "1")
    val numberOfPeople: Int?,

    @Schema(description = "일 수", example = "1박 2일")
    val numberOfDays: String?,

    @Schema(description = "루트 작성 완료일", example = "2021-08-01T00:00:00")
    val createdAt: LocalDateTime?,
) {
    companion object {
        fun from(
            getRouteDetailResult: GetRouteDetailResult,
        ): RouteResponse = RouteResponse(
            routeId = getRouteDetailResult.routeId,
            userId = getRouteDetailResult.userId,
            profileImageUrl = getRouteDetailResult.profileImageUrl,
            nickname = getRouteDetailResult.nickname,
            routeName = getRouteDetailResult.routeName,
            routeDescription = getRouteDetailResult.routeDescription,
            routeImageUrls = getRouteDetailResult.routeImageUrls,
            isPurchased = getRouteDetailResult.isPurchased,
            purchaseCount = getRouteDetailResult.purchaseCount,
            commentCount = getRouteDetailResult.commentCount,
            routeStyles = getRouteDetailResult.routeStyles,
            whoWith = getRouteDetailResult.whoWith,
            transportation = getRouteDetailResult.transportation,
            numberOfPeople = getRouteDetailResult.numberOfPeople,
            numberOfDays = getRouteDetailResult.numberOfDays,
            createdAt = getRouteDetailResult.recordFinishedAt,
        )
    }
}
