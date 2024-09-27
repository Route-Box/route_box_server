package com.routebox.routebox.application.route.dto

import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalTime

data class UpdateRouteActivityCommand(
    val activityId: Long,
    val locationName: String,
    val address: String,
    val latitude: String?,
    val longitude: String?,
    val visitDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val category: String,
    val description: String?,
    val addedImages: List<MultipartFile>,
    val deletedImageIds: List<Long>,
)
