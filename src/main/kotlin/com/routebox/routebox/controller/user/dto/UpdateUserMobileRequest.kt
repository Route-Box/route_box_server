package com.routebox.routebox.controller.user.dto

import com.routebox.routebox.application.user_mobile.dto.UpdateUserMobileCommand
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateUserMobileRequest(
    @Schema(description = "OS (iOS, Android)")
    var os: String,

    @Schema(description = "FCM 토큰")
    var pushToken: String,
) {
    fun toCommand(userId: Long): UpdateUserMobileCommand =
        UpdateUserMobileCommand(
            userId = userId,
            os = os,
            pushToken = pushToken,
        )
}
