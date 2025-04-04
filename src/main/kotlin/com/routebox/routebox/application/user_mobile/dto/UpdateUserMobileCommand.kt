package com.routebox.routebox.application.user_mobile.dto

data class UpdateUserMobileCommand(
    val userId: Long,
    val os: String,
    val pushToken: String?,
)
