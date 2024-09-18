package com.routebox.routebox.application.recommended_route

import com.routebox.routebox.application.recommended_route.dto.RecommendedRouteDto
import com.routebox.routebox.domain.recommended_route.RecommendedRouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetRecommendedRoutesUseCase(
    private val recommendedRouteService: RecommendedRouteService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(): List<RecommendedRouteDto> {
        return recommendedRouteService.getRecommendRoutes()
    }
}
