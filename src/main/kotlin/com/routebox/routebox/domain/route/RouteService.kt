package com.routebox.routebox.domain.route

import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts.asc
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts.desc
import com.routebox.routebox.application.route.dto.CreateRoutePointCommand
import com.routebox.routebox.application.route.dto.SearchCommand
import com.routebox.routebox.controller.route.dto.RouteSortBy
import com.routebox.routebox.domain.common.FileManager
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.exception.route.RouteNotFoundException
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
     * 루트 탐색 - 최신순 루트 조회, 공개된 루트만, 신고된 루트 제외
     */
    @Transactional(readOnly = true)
    fun getLatestRoutes(userId: Long, page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAllFiltered(userId, pageable).content
    }

    /**
     * 루트 상세 조회
     */
    @Transactional(readOnly = true)
    fun findRouteById(id: Long): Route? = routeRepository.findById(id).orElse(null)

    @Transactional(readOnly = true)
    fun findRoutesByIds(ids: List<Long>): List<Route> = routeRepository.findByIdIn(ids)

    /**
     * 루트 단건 조회
     *
     * @throws RouteNotFoundException id와 일치하는 루트가 없는 경우
     */
    @Transactional(readOnly = true)
    fun getRouteById(id: Long): Route = this.findRouteById(id) ?: throw RouteNotFoundException()

    /**
     * 유저 id로 유저가 작성한 루트 개수 조회하기
     *
     * @param userId id of user
     * @return userId에 해당하는 유저가 작성한 루트 개수
     */
    @Transactional(readOnly = true)
    fun countRoutesByUserId(userId: Long) = routeRepository.countByUser_Id(userId)

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
            styles = "",
            style = emptyArray(),
            transportation = null,
            isPublic = false,
        )
        return routeRepository.save(route)
    }

    /**
     * 루트 점찍기
     */
    @Transactional
    fun createRoutePoints(commands: List<CreateRoutePointCommand>): List<RoutePoint> {
        val routePoints: List<RoutePoint> = commands.map {
            val route = routeRepository.findById(it.routeId).orElseThrow { IllegalArgumentException("Route not found") }
            RoutePoint(
                route = route,
                latitude = it.latitude,
                longitude = it.longitude,
                recordAt = it.recordAt,
            )
        }
        return routePointRepository.saveAll(routePoints)
    }

    /**
     * 루트 점찍기 (Legacy)
     * TODO: 배열 point 적용 이후 삭제 필요
     */
    @Transactional
    fun createRoutePoint(routeId: Long, latitude: String, longitude: String, recordAt: LocalDateTime): RoutePoint {
        val route = findRouteById(routeId) ?: throw IllegalArgumentException("Route not found")
        val routePoint = RoutePoint(
            route = route,
            latitude = latitude,
            longitude = longitude,
            recordAt = recordAt,
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
        val route = findRouteById(routeId) ?: throw IllegalArgumentException("Route not found")

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
        style: Array<String>?,
        transportation: String?,
    ): Route {
        val route = findRouteById(routeId) ?: throw IllegalArgumentException("Route not found")

        if (name != null) route.updateName(name)

        if (description != null) route.updateDescription(description)

        if (whoWith != null) route.updateWhoWith(whoWith)

        if (numberOfPeople != null) route.updateNumberOfPeople(numberOfPeople)

        if (numberOfDays != null) route.updateNumberOfDays(numberOfDays)

        if (style !== null && style.isNotEmpty()) route.updateStyle(style)

        if (transportation != null) route.updateTransportation(transportation)

        return routeRepository.save(route)
    }

    /**
     * 루트 삭제
     */
    @Transactional
    fun deleteRoute(routeId: Long) {
        val route = findRouteById(routeId) ?: throw IllegalArgumentException("Route not found")

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
        val route = findRouteById(routeId) ?: throw IllegalArgumentException("Route not found")
        route.updatePublic(isPublic)
        return routeRepository.save(route)
    }

    /**
     * 기록중인 루트 조회
     */
    @Transactional(readOnly = true)
    fun getProgressRouteByUserId(userId: Long): Route? = routeRepository.findByRecordFinishedAtIsNullAndUser_Id(userId).firstOrNull()

    /**
     * 내 루트 목록조회
     */
    @Transactional(readOnly = true)
    fun getMyRoutes(userId: Long): List<Route> = routeRepository.findByUser_IdAndRecordFinishedAtIsNotNullOrderByRecordFinishedAtDesc(userId)

    /**
     * 사용자 루트 목록 조회
     */
    @Transactional(readOnly = true)
    fun getRoutesByUserId(userId: Long): List<Route> = routeRepository.findByUser_IdAndIsPublicOrderByRecordFinishedAtDesc(userId, true)

    /**
     * 루트 마무리
     */
    @Transactional
    fun finishRecordRoute(routeId: Long, name: String, description: String?): Route {
        val route = findRouteById(routeId) ?: throw IllegalArgumentException("Route not found")
        route.finishRecord(name, description)
        return routeRepository.save(route)
    }

    /**
     * 루트 활동 조회
     */
    @Transactional(readOnly = true)
    fun findRouteActivityById(activityId: Long): RouteActivity? = routeActivityRepository.findById(activityId).orElse(null)

    /**
     * 루트 검색
     */
    @Transactional(readOnly = true)
    fun searchRoutes(request: SearchCommand): List<Route> {
        val pageable = PageRequest.of(request.page, request.size)
        return routeRepository.findPage(pageable) {
            select(
                entity(Route::class),
            ).from(
                entity(Route::class),
            ).where(
                and(
                    path(Route::isPublic).eq(true),
                    request.query?.let {
                        or(
                            path(Route::name).like("%$it%"),
                            path(Route::description).like("%$it%"),
                        )
                    },
                    request.filters.whoWith.takeIf { it.isNotEmpty() }?.let {
                        path(Route::whoWith).`in`(it)
                    },
                    request.filters.numberOfPeople.takeIf { it.isNotEmpty() }?.let {
                        path(Route::numberOfPeople).`in`(it)
                    },
                    request.filters.numberOfDays.takeIf { it.isNotEmpty() }?.let {
                        path(Route::numberOfDays).`in`(it)
                    },
                    request.filters.style.takeIf { it.isNotEmpty() }?.let { stylesFilter ->
                        and(
                            *stylesFilter.map { style ->
                                path(Route::styles).like("%$style%")
                            }.toTypedArray(),
                        )
                    },
                    request.filters.transportation.takeIf { it.isNotEmpty() }?.let {
                        path(Route::transportation).`in`(it)
                    },
                ),
            ).orderBy(
                when (request.sortBy) {
                    RouteSortBy.NEWEST -> desc(path(Route::createdAt))
                    RouteSortBy.OLDEST -> asc(path(Route::createdAt))
                    RouteSortBy.POPULAR -> asc(path(Route::createdAt)) // TODO: implements
                    RouteSortBy.COMMENTS -> asc(path(Route::createdAt)) // TODO: implements
                },
            )
        }.content.filterNotNull()
    }

    /**
     * 내루트 개수 조회
     */
    fun getMyRouteCount(userId: Long): Int =
        routeRepository.countByUser_IdAndRecordFinishedAtIsNotNull(userId)
}
