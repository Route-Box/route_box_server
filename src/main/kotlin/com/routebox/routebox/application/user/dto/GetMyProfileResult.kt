package com.routebox.routebox.application.user.dto

import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.Gender
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class GetMyProfileResult(
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
) {
    companion object {
        fun from(user: User) = GetMyProfileResult(
            id = user.id,
            profileImageUrl = user.profileImageUrl,
            nickname = user.nickname,
            gender = user.gender,
            birthDay = user.birthDay,
            introduction = user.introduction,
        )
    }
}
