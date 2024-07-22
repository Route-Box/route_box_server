package com.routebox.routebox.controller.user.dto

import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.validation.Nickname
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class UpdateUserInfoRequest(

    @Schema(description = "닉네임", example = "고작가")
    @field:Nickname
    val nickname: String?,

    @Schema(description = "성별")
    val gender: Gender?,

    @Schema(description = "생일")
    val birthDay: LocalDate?,

    @Schema(description = "한 줄 소개", example = "평범한 일상 속의 예술을 이야기합니다...")
    @field:Length(max = 25)
    val introduction: String?,
)
