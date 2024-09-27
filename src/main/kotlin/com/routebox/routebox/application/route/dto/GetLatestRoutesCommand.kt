package com.routebox.routebox.application.route.dto

data class GetLatestRoutesCommand(
    val userId: Long,
    val page: Int,
    val size: Int,
)
