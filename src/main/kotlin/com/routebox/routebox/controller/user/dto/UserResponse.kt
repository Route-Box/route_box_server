package com.routebox.routebox.controller.user.dto

import com.routebox.routebox.application.user.dto.UpdateUserInfoResult
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.Gender
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class UserResponse(
    @Schema(description = "Id(PK) of user", example = "1")
    val id: Long,

    @Schema(description = "유저 프로필 이미지(url)", example = "https://user-profile-image")
    val profileImageUrl: String,

    @Schema(description = "닉네임", example = "고작가")
    val nickname: String,

    @Schema(description = "현재 잔여 포인트", example = "500")
    val point: Int,

    @Schema(description = "성별")
    val gender: Gender,

    @Schema(description = "생일")
    val birthDay: LocalDate,

    @Schema(description = "한 줄 소개", example = "평범한 일상 속의 예술을 찾고 있습니다...")
    val introduction: String,
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.id,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            point = user.point,
            gender = user.gender,
            birthDay = user.birthDay,
            introduction = user.introduction,
        )

        fun from(updateUserInfoResult: UpdateUserInfoResult): UserResponse = UserResponse(
            id = updateUserInfoResult.id,
            profileImageUrl = updateUserInfoResult.profileImageUrl,
            nickname = updateUserInfoResult.nickname,
            point = updateUserInfoResult.point,
            gender = updateUserInfoResult.gender,
            birthDay = updateUserInfoResult.birthDay,
            introduction = updateUserInfoResult.introduction,
        )
    }
}
