package com.routebox.routebox.controller.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateUserMobileResponse(
    @Schema(description = "Id(PK) of user mobile", example = "1")
    val id: Long,
)
