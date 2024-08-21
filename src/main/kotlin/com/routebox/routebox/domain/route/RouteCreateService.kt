package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.FileManager
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.infrastructure.route.RouteActivityImageRepository
import com.routebox.routebox.infrastructure.route.RouteActivityRepository
import com.routebox.routebox.infrastructure.route.RoutePointRepository
import com.routebox.routebox.infrastructure.route.RouteRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class RouteCreateService(
    private val routeRepository: RouteRepository,
    private val routePointRepository: RoutePointRepository,
    private val routeActivityRepository: RouteActivityRepository,
    private val routeActivityImageRepository: RouteActivityImageRepository,
    private val fileManager: FileManager,
) {
    companion object {
        const val ROUTE_ACTIVITY_IMAGE_UPLOAD_PATH = "route-activity-images/"
    }

    /**
     * 루트 탐색 - 최신순 루트 조회
     */
    @Transactional(readOnly = true)
    fun getLatestRoutes(page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAllByOrderByCreatedAtDesc(pageable).content
    }

    /**
     * 루트 상세 조회
     */
    @Transactional(readOnly = true)
    fun getRouteById(id: Long): Route? = routeRepository.findById(id).orElse(null)

    /**
     * 루트 생성
     */
    @Transactional
    fun createRoute(user: User, startTime: LocalDateTime, endTime: LocalDateTime): Route {
        val route = Route(
            user = user,
            startTime = startTime,
            endTime = endTime,
            name = null,
            description = null,
            whoWith = null,
            numberOfPeople = null,
            numberOfDays = null,
            style = emptyArray(),
            transportation = emptyArray(),
            transportations = null,
            isPublic = false,
        )
        return routeRepository.save(route)
    }

    /**
     * 루트 점찍기
     */
    @Transactional
    fun createRoutePoint(routeId: Long, latitude: String, longitude: String, pointOrder: Int): RoutePoint {
        val route = getRouteById(routeId) ?: throw IllegalArgumentException("Route not found")
        val routePoint = RoutePoint(
            route = route,
            latitude = latitude,
            longitude = longitude,
            pointOrder = pointOrder,
        )
        return routePointRepository.save(routePoint)
    }

    /**
     * 루트 활동 추가
     */
    @Transactional
    fun createRouteActivity(
        routeId: Long,
        locationName: String,
        address: String,
        latitude: String?,
        longitude: String?,
        visitDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        category: String,
        description: String?,
        images: List<MultipartFile>,
    ): RouteActivity {
        // 루트 조회
        val route = getRouteById(routeId) ?: throw IllegalArgumentException("Route not found")

        // 루트 활동 생성
        val routeActivity = RouteActivity(
            route = route,
            locationName = locationName,
            address = address,
            latitude = latitude,
            longitude = longitude,
            visitDate = visitDate,
            startTime = startTime,
            endTime = endTime,
            category = category,
            description = description,
        )
        val savedActivity = routeActivityRepository.save(routeActivity)

        // 이미지 처리
        images.forEach { image ->
            try {
                // 이미지 업로드
                val (storedFileName, fileUrl) = fileManager.upload(image, ROUTE_ACTIVITY_IMAGE_UPLOAD_PATH)

                // 이미지 엔티티 생성
                val activityImage = RouteActivityImage(
                    activity = savedActivity,
                    storedFileName = storedFileName,
                    fileUrl = fileUrl,
                )
                // 이미지 저장
                savedActivity.addActivityImage(activityImage)
                routeActivityImageRepository.save(activityImage)
            } catch (e: IOException) {
                throw RuntimeException("Failed to upload image", e)
            }
        }

        return savedActivity
    }
}
