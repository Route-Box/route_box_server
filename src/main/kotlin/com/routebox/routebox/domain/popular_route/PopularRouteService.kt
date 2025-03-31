package com.routebox.routebox.domain.popular_route

import com.routebox.routebox.application.popular_route.dto.PopularRouteCountDto
import com.routebox.routebox.application.popular_route.dto.PopularRouteDto
import com.routebox.routebox.infrastructure.popular_route.PopularRouteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class PopularRouteService(
    private val popularRouteRepository: PopularRouteRepository,
) {

    /**
     * 인기 루트 조회
     */
    @Transactional(readOnly = true)
    fun getPopularRoutes(): List<PopularRouteDto> {
        // 가장 최근에 업데이트된 인기루트 조회
        return popularRouteRepository.findRecentPopularRoutes()
    }

    /**
     * 인기 루트 추가
     */
    @Transactional
    fun createPopularRoutes(routes: List<PopularRouteCountDto>) {
        val today = LocalDate.now()
        val popularRoutes = routes.map { route ->
            PopularRoute(
                routeId = route.id,
                count = route.count,
                date = today,
            )
        }

        popularRouteRepository.saveAll(popularRoutes)
    }
}
