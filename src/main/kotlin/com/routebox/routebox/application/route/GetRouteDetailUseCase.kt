package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component

@Component
class GetRouteDetailUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 상세 조회.
     *
     * @param command route id
     * @return 루트 상세 정보
     * @throws // TODO
     */
    operator fun invoke(id: Long): GetRouteDetailResult {
        val route = routeService.getRouteById(id).let {
            it ?: throw IllegalArgumentException("Route not found")
        }

        return GetRouteDetailResult.from(route)
    }
}
