package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.application.route.dto.UpdateRouteResult
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateRouteResponse(
    @Schema(description = "루트 ID", example = "1")
    val routeId: Long,
    @Schema(description = "루트 이름", example = "제주도 여행")
    val routeName: String?,
    @Schema(description = "루트 설명", example = "제주도 여행을 즐기자")
    val routeDescription: String?,
    @Schema(description = "함께 여행한 사람", example = "친구")
    val whoWith: String?,
    @Schema(description = "여행 인원", example = "4")
    val numberOfPeople: Int?,
    @Schema(description = "여행 일수", example = "3")
    val numberOfDays: String?,
    @Schema(description = "여행 스타일", example = "[\"힐링\", \"힐링\"]")
    val routeStyles: Array<String>,
    @Schema(description = "이동 수단", example = "비행기")
    val transportation: String?,
) {
    companion object {
        fun from(
            result: UpdateRouteResult,
        ): UpdateRouteResponse {
            return UpdateRouteResponse(
                routeId = result.routeId,
                routeName = result.name,
                routeDescription = result.description,
                whoWith = result.whoWith,
                numberOfPeople = result.numberOfPeople,
                numberOfDays = result.numberOfDays,
                routeStyles = result.style,
                transportation = result.transportation,
            )
        }
    }
}
