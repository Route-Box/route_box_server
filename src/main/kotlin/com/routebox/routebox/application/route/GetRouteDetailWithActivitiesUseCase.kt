package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetRouteDetailWithActivitiesResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetRouteDetailWithActivitiesUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 상세 조회. (활동, 경로 포함)
     *
     * @param command route id
     * @return 루트 상세 정보
     * @throws // TODO
     */
    @Transactional(readOnly = true)
    operator fun invoke(id: Long): GetRouteDetailWithActivitiesResult {
        val route = routeService.getRouteById(id).let {
            it ?: throw IllegalArgumentException("Route not found")
        }

        return GetRouteDetailWithActivitiesResult.from(route)
    }
}
