package com.routebox.routebox.application.route.dto

data class UpdateRoutePublicCommand(
    val userId: Long,
    val routeId: Long,
    val isPublic: Boolean,
)
