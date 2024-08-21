package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.UpdateRouteCommand
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateRouteRequest(
    @Schema(description = "루트 이름", example = "제주도 여행")
    val routeName: String,
    @Schema(description = "루트 설명", example = "제주도 여행을 즐기자")
    val routeDescription: String,
    @Schema(description = "함께 여행한 사람", example = "친구")
    val whoWith: String,
    @Schema(description = "여행 인원", example = "4")
    val numberOfPeople: Int,
    @Schema(description = "여행 일수", example = "3박4일")
    val numberOfDays: String,
    @Schema(description = "여행 스타일", example = "[\"힐링\", \"힐링\"]")
    val routeStyles: Array<String>,
    @Schema(description = "이동 수단", example = "비행기")
    val transportation: String,
) {
    fun toCommand(userId: Long, routeId: Long): UpdateRouteCommand =
        UpdateRouteCommand(
            userId = userId,
            routeId = routeId,
            name = routeName,
            description = routeDescription,
            whoWith = whoWith,
            numberOfPeople = numberOfPeople,
            numberOfDays = numberOfDays,
            style = routeStyles,
            transportation = transportation,
        )
}
