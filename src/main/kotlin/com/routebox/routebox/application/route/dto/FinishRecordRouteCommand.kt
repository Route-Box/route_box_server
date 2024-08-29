package com.routebox.routebox.application.route.dto

data class FinishRecordRouteCommand(
    val routeId: Long,
    val name: String,
    val description: String?,
)
