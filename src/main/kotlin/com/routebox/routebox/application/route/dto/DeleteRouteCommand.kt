package com.routebox.routebox.application.route.dto

data class DeleteRouteCommand(
    val userId: Long,
    val routeId: Long,
)
