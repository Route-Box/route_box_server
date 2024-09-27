package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.GetRouteDetailWithActivitiesResult
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class RouteDetailResponse(
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

    @Schema(description = "루트 시작일시")
    val startTime: LocalDateTime,

    @Schema(description = "루트 종료일시")
    val endTime: LocalDateTime,

    @Schema(description = "함께한 사람")
    val whoWith: String?,

    @Schema(description = "루트 스타일들", example = "[\"뚜벅뚜벅\"]")
    val routeStyles: List<String>,

    @Schema(description = "인원 수")
    val numberOfPeople: Int?,

    @Schema(description = "여행일수")
    val numberOfDays: String?,

    @Schema(description = "교통수단")
    val transportation: String?,

    @Schema(description = "공개 여부")
    val isPublic: Boolean,

    @Schema(description = "루트 작성 완료일", example = "2021-08-01T00:00:00")
    val createdAt: String?,

    @Schema(description = "루트 경로", example = "[{\"latitude\": 37.1234, \"longitude\": 127.1234}]")
    val routePath: List<Map<String, String>>,

    @Schema(description = "루트 활동 목록")
    val routeActivities: List<RouteActivityResponse>,
) {
    companion object {
        fun from(result: GetRouteDetailWithActivitiesResult): RouteDetailResponse = RouteDetailResponse(
            routeId = result.routeId,
            userId = result.userId,
            profileImageUrl = result.profileImageUrl,
            nickname = result.nickname,
            routeName = result.routeName,
            routeDescription = result.routeDescription,
            startTime = LocalDateTime.parse(result.startTime),
            endTime = LocalDateTime.parse(result.endTime),
            whoWith = result.whoWith,
            routeStyles = result.routeStyles,
            numberOfPeople = result.numberOfPeople,
            numberOfDays = result.numberOfDays,
            transportation = result.transportation,
            isPublic = result.isPublic,
            createdAt = result.recordFinishedAt.toString(),
            routePath = result.routePath,
            routeActivities = result.routeActivities.map { RouteActivityResponse.from(it) },
        )
    }
}
