package com.routebox.routebox.controller.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CheckNicknameAvailabilityResponse(
    @Schema(description = "이용 가능 여부를 확인하고자 한 닉네임.", example = "고작가")
    val nickname: String,

    @Schema(description = "닉네임의 이용 가능 여부. <code>true</code>인 경우, 이용 가능한 닉네임임.")
    val isAvailable: Boolean,
)
