package com.routebox.routebox.domain.recommended_route

import com.routebox.routebox.application.recommended_route.dto.RecommendedRouteDto
import com.routebox.routebox.infrastructure.recommended_route.RecommendedRouteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecommendedRouteService(
    private val recommendedRouteRepository: RecommendedRouteRepository,
) {

    /**
     * 추천 루트 조회
     */
    @Transactional(readOnly = true)
    fun getRecommendRoutes(): List<RecommendedRouteDto> {
        return recommendedRouteRepository.findAllRecommendedRoutes()
    }
}
