package com.routebox.routebox.application.user.dto

import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.Gender
import java.time.LocalDate

data class UpdateUserInfoResult(
    val id: Long,
    val profileImageUrl: String,
    val nickname: String,
    val point: Int,
    val gender: Gender,
    val birthDay: LocalDate,
    val introduction: String,
) {
    companion object {
        fun from(user: User): UpdateUserInfoResult = UpdateUserInfoResult(
            id = user.id,
            profileImageUrl = user.profileImageUrl,
            nickname = user.nickname,
            point = user.point,
            gender = user.gender,
            birthDay = user.birthDay,
            introduction = user.introduction,
        )
    }
}
