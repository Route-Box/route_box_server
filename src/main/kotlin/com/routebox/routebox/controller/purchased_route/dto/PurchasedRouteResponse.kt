package com.routebox.routebox.controller.purchased_route.dto

import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import com.routebox.routebox.domain.purchased_route.PurchasedRouteActivity
import com.routebox.routebox.domain.purchased_route.PurchasedRoutePoint
import com.routebox.routebox.domain.user.User
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class PurchasedRouteResponse(
    @Schema(description = "Id of purchased route")
    val id: Long,

    @Schema(description = "루트 작성자 정보")
    val writer: UserResponse,

    @Schema(description = "제목", example = "공주들이랑 함께한 경주 여행")
    val name: String?,

    @Schema(description = "설명", example = "이런저런 얘기들이 들어갑니다...")
    val description: String?,

    @Schema(description = "함께한 사람들", example = "혼자")
    val whoWith: String?,

    @Schema(description = "인원 수")
    val numberOfPeoples: Int?,

    @Schema(description = "일 수", example = "1박 2일")
    val numberOfDays: String?,

    @Schema(description = "루트 스타일", example = "[\"뚜벅뚜벅\"]")
    val styles: List<String>,

    @Schema(description = "이동수단", example = "도보")
    val transportation: String?,

    @Schema(description = "루트 포인트(들)")
    val routePoints: List<PurchasedRoutePointResponse>,

    @Schema(description = "루트 활동(들)")
    val routeActivities: List<PurchasedRouteActivityResponse>,
) {
    companion object {
        fun fromPurchasedRoute(route: PurchasedRoute): PurchasedRouteResponse =
            PurchasedRouteResponse(
                id = route.id,
                writer = UserResponse.fromUser(route.writer),
                name = route.name,
                description = route.description,
                whoWith = route.whoWith,
                numberOfPeoples = route.numberOfPeoples,
                numberOfDays = route.numberOfDays,
                styles = route.styles,
                transportation = route.transportation,
                routePoints = route.routePoints.map { PurchasedRoutePointResponse.fromPurchasedRoutePoint(it) },
                routeActivities = route.routeActivities
                    .map { PurchasedRouteActivityResponse.fromPurchasedRouteActivity(it) },
            )
    }

    data class UserResponse(
        @Schema(description = "Id of user")
        val id: Long,

        @Schema(description = "프로필 이미지 url", example = "https://user-profile-image")
        val profileImageUrl: String,

        @Schema(description = "닉네임", example = "귤히어로")
        val nickname: String,
    ) {
        companion object {
            fun fromUser(user: User): UserResponse = UserResponse(
                id = user.id,
                profileImageUrl = user.profileImageUrl,
                nickname = user.nickname,
            )
        }
    }

    data class PurchasedRoutePointResponse(
        @Schema(description = "Id of route point")
        val routePointId: Long,

        @Schema(description = "위도", example = "37.123")
        val latitude: String,

        @Schema(description = "경도", example = "127.123")
        val longitude: String,

        @Schema(description = "기록 시각")
        val recordAt: LocalDateTime,
    ) {
        companion object {
            fun fromPurchasedRoutePoint(point: PurchasedRoutePoint): PurchasedRoutePointResponse =
                PurchasedRoutePointResponse(
                    routePointId = point.routePointId,
                    latitude = point.latitude,
                    longitude = point.longitude,
                    recordAt = point.recordAt,
                )
        }
    }

    data class PurchasedRouteActivityResponse(
        @Schema(description = "Id of route activity")
        val routeActivityId: Long,

        @Schema(description = "장소 이름", example = "강릉 해파랑물회")
        val locationName: String,

        @Schema(description = "주소", example = "강릉시 경포동 경포로")
        val address: String,

        @Schema(description = "위도", example = "37.123")
        val latitude: String?,

        @Schema(description = "경도", example = "127.123")
        val longitude: String?,

        @Schema(description = "방문 일자")
        val visitDate: LocalDate,

        @Schema(description = "방문 시작 시간")
        val startTime: LocalTime,

        @Schema(description = "방문 종료 시간")
        val endTime: LocalTime,

        @Schema(description = "카테고리", example = "관광명소")
        val category: String,

        @Schema(description = "설명", example = "장소에 대한 설명이 들어갑니다...")
        val description: String?,

        @Schema(description = "활동에 대한 이미지(들)", example = "[\"https://image-1\", \"https://image-2\"]")
        val activityImageUrls: List<String>,
    ) {
        companion object {
            fun fromPurchasedRouteActivity(activity: PurchasedRouteActivity): PurchasedRouteActivityResponse =
                PurchasedRouteActivityResponse(
                    routeActivityId = activity.routeActivityId,
                    locationName = activity.locationName,
                    address = activity.address,
                    latitude = activity.latitude,
                    longitude = activity.longitude,
                    category = activity.category,
                    visitDate = activity.visitDate,
                    startTime = activity.startTime,
                    endTime = activity.endTime,
                    description = activity.description,
                    activityImageUrls = activity.activityImageUrls,
                )
        }
    }
}
