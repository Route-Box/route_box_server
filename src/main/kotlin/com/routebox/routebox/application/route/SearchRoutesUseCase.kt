package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import com.routebox.routebox.application.route.dto.SearchCommand
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component

@Component
class SearchRoutesUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 검색.
     *
     * @param request 검색 조건
     * @return 루트 상세 정보
     * @throws
     */
    operator fun invoke(request: SearchCommand): List<GetRouteDetailResult> {
        val routes = routeService.searchRoutes(request).map { GetRouteDetailResult.from(it) }
        return routes
    }
}
