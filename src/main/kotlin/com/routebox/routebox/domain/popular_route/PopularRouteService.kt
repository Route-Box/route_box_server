package com.routebox.routebox.domain.popular_route

import com.routebox.routebox.application.popular_route.dto.PopularRouteDto
import com.routebox.routebox.infrastructure.popular_route.PopularRouteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PopularRouteService(
    private val popularRouteRepository: PopularRouteRepository,
) {

    /**
     * 인기 루트 조회
     */
    @Transactional(readOnly = true)
    fun getPopularRoutes(): List<PopularRouteDto> {
        return popularRouteRepository.findAllPopularRoutes()
    }
}
