package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.user.User
import com.routebox.routebox.infrastructure.route.RouteRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RouteService(private val routeRepository: RouteRepository) {
    /**
     * 루트 탐색 - 최신순 루트 조회
     */
    fun getLatestRoutes(page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAllByOrderByCreatedAtDesc(pageable).content
    }

    /**
     * 루트 상세 조회
     */
    fun getRouteById(id: Long): Route? = routeRepository.findById(id).orElse(null)

    /**
     * 루트 생성
     */
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
            isPublic = false,
        )
        return routeRepository.save(route)
    }
}
