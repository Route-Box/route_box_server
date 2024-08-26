package com.routebox.routebox.application.route.dto

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalTime

data class CreateRouteActivityCommand(
    val userId: Long,
    val routeId: Long,
    val locationName: String,
    val address: String,
    val latitude: String?,
    val longitude: String?,
    val visitDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val category: String,
    val description: String?,
    val images: List<MultipartFile>,
)
