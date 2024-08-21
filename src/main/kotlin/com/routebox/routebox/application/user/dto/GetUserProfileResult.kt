package com.routebox.routebox.application.user.dto

import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.Gender
import java.time.LocalDate

data class GetUserProfileResult(
    val id: Long,
    val profileImageUrl: String,
    val nickname: String,
    val gender: Gender,
    val birthDay: LocalDate,
    val introduction: String,
    val numOfRoutes: Int,

    val mostVisitedLocation: String,
    val mostTaggedRouteStyles: String,
) {
    companion object {
        fun from(user: User, numOfRoutes: Int) = GetUserProfileResult(
            id = user.id,
            profileImageUrl = user.profileImageUrl,
            nickname = user.nickname,
            gender = user.gender,
            birthDay = user.birthDay,
            introduction = user.introduction,
            numOfRoutes = numOfRoutes,

            // TODO: 기획 문의, 루트 쪽 관련 코드 구현 필요. 기획 문의 및 루트 구현 완료 후 구현 예정
            mostVisitedLocation = "경주",
            mostTaggedRouteStyles = "가족여행",
        )
    }
}
