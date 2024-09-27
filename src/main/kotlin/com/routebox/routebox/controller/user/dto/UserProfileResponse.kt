package com.routebox.routebox.controller.user.dto

import com.routebox.routebox.application.user.dto.GetUserProfileResult
import com.routebox.routebox.domain.user.constant.Gender
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class UserProfileResponse(
    @Schema(description = "Id(PK) of user", example = "1")
    val id: Long,

    @Schema(description = "유저 프로필 이미지(url)", example = "https://user-profile-image")
    val profileImageUrl: String,

    @Schema(description = "닉네임", example = "고작가")
    val nickname: String,

    @Schema(description = "성별")
    val gender: Gender,

    @Schema(description = "생일")
    val birthDay: LocalDate,

    @Schema(description = "한 줄 소개", example = "평범한 일상 속의 예술을 찾고 있습니다...")
    val introduction: String,

    @Schema(description = "작성한 루트 개수", example = "3")
    val numOfRoutes: Int,

    @Schema(description = "(취향 정보) 가장 많이 방문한 지역", example = "경주")
    val mostVisitedLocation: String,

    @Schema(description = "(취향 정보) 가장 많이 태그한 루트 스타일", example = "가족여행")
    val mostTaggedRouteStyles: String,
) {
    companion object {
        fun fromResult(getUserProfileResult: GetUserProfileResult) =
            UserProfileResponse(
                id = getUserProfileResult.id,
                profileImageUrl = getUserProfileResult.profileImageUrl,
                nickname = getUserProfileResult.nickname,
                gender = getUserProfileResult.gender,
                birthDay = getUserProfileResult.birthDay,
                introduction = getUserProfileResult.introduction,
                numOfRoutes = getUserProfileResult.numOfRoutes,
                mostVisitedLocation = getUserProfileResult.mostVisitedLocation,
                mostTaggedRouteStyles = getUserProfileResult.mostTaggedRouteStyles,
            )
    }
}
