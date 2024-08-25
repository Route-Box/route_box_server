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
class RouteService(
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
    fun getRouteById(id: Long): Route? =
        routeRepository.findById(id).orElse(null)

    /**
     * 유저 id로 유저가 작성한 루트 개수 조회하기
     *
     * @param userId id of user
     * @return userId에 해당하는 유저가 작성한 루트 개수
     */
    @Transactional(readOnly = true)
    fun countRoutesByUserId(userId: Long) =
        routeRepository.countByUser_Id(userId)

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
                val (storedFileName, fileUrl) = fileManager.upload(
                    image,
                    ROUTE_ACTIVITY_IMAGE_UPLOAD_PATH,
                )

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

    /**
     * 루트 활동 수정
     */
    @Transactional
    fun updateRouteActivity(
        activityId: Long,
        locationName: String,
        address: String,
        latitude: String?,
        longitude: String?,
        visitDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime,
        category: String,
        description: String?,
        addedImages: List<MultipartFile>,
        deletedImageIds: List<Long>,
    ): RouteActivity {
        // 루트 활동 조회
        val routeActivity = routeActivityRepository.findById(activityId)
            .orElseThrow { IllegalArgumentException("Route activity not found") }

        // 루트 활동 수정
        routeActivity.update(
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

        // 추가된 이미지 처리
        addedImages.forEach { image ->
            try {
                // 이미지 업로드
                val (storedFileName, fileUrl) = fileManager.upload(
                    image,
                    ROUTE_ACTIVITY_IMAGE_UPLOAD_PATH,
                )

                // 이미지 엔티티 생성
                val activityImage = RouteActivityImage(
                    activity = routeActivity,
                    storedFileName = storedFileName,
                    fileUrl = fileUrl,
                )

                // 이미지 저장
                routeActivity.addActivityImage(activityImage)
                routeActivityImageRepository.save(activityImage)
            } catch (e: IOException) {
                throw RuntimeException("Failed to upload image", e)
            }
        }

        // 삭제된 이미지 처리
        deletedImageIds.forEach { imageId ->
            val image = routeActivity.activityImages.find { it.id == imageId }
                ?: throw IllegalArgumentException("Image not found")
            image.delete()
        }

        // TODO: s3 이미지 삭제

        return routeActivityRepository.save(routeActivity)
    }

    /**
     * 루트 활동 삭제
     */
    @Transactional
    fun deleteRouteActivity(activityId: Long) {
        val routeActivity = routeActivityRepository.findById(activityId)
            .orElseThrow { IllegalArgumentException("Route activity not found") }
        routeActivityRepository.delete(routeActivity)
    }

    /**
     * 루트 수정
     */
    @Transactional
    fun updateRoute(
        routeId: Long,
        name: String?,
        description: String?,
        whoWith: String?,
        numberOfPeople: Int?,
        numberOfDays: String?,
        style: Array<String>,
        transportation: String?,
    ): Route {
        val route = getRouteById(routeId) ?: throw IllegalArgumentException("Route not found")
        route.update(
            name = name,
            description = description,
            whoWith = whoWith,
            numberOfPeople = numberOfPeople,
            numberOfDays = numberOfDays,
            style = style,
            transportation = transportation,
        )
        return routeRepository.save(route)
    }

    /**
     * 루트 삭제
     */
    @Transactional
    fun deleteRoute(routeId: Long) {
        val route = getRouteById(routeId) ?: throw IllegalArgumentException("Route not found")

        // 루트 위치 삭제
        routePointRepository.deleteAll(route.routePoints)

        // 루트 활동 이미지 삭제
        routeActivityImageRepository.deleteAll(route.routeActivities.flatMap { it.activityImages })

        // 루트 활동 삭제
        routeActivityRepository.deleteAll(route.routeActivities)

        // 루트 삭제
        routeRepository.delete(route)
    }

    /**
     * 루트 공개 여부 수정
     */
    @Transactional
    fun updateRoutePublic(routeId: Long, isPublic: Boolean): Route {
        val route = getRouteById(routeId) ?: throw IllegalArgumentException("Route not found")
        route.updatePublic(isPublic)
        return routeRepository.save(route)
    }

    /**
     * 기록중인 루트 조회
     */
    @Transactional(readOnly = true)
    fun getProgressRouteByUserId(now: LocalDateTime, userId: Long): Route? =
        routeRepository.findByEndTimeIsAfterAndUser_Id(now, userId)

    /**
     * 내 루트 목록조회
     */
    @Transactional(readOnly = true)
    fun getMyRoutes(userId: Long): List<Route> =
        routeRepository.findByUser_IdOrderByCreatedAtDesc(userId)
}
