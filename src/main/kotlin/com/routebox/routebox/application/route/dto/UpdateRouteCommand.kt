package com.routebox.routebox.application.route.dto

data class UpdateRouteCommand(
    val userId: Long,
    val routeId: Long,
    val name: String,
    val description: String,
    val whoWith: String,
    val numberOfPeople: Int,
    val numberOfDays: String,
    val style: Array<String>,
    val transportation: String,
)
