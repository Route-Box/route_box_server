package com.routebox.routebox.application.popular_route

import com.routebox.routebox.application.popular_route.dto.PopularRouteDto
import com.routebox.routebox.domain.popular_route.PopularRouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetPopularRoutesUseCase(
    private val popularRouteService: PopularRouteService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(): List<PopularRouteDto> {
        return popularRouteService.getPopularRoutes()
    }
}
