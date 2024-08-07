package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component

/**
 * 루트 최신순 조회
 *
 * @param page 페이지 번호
 * @param size 페이지 크기
 * @return 루트 목록
 */
@Component
class GetLatestRoutesUseCase(
    private val routeService: RouteService,
) {
    operator fun invoke(page: Int, size: Int): List<GetRouteDetailResult> {
        val routes = routeService.getLatestRoutes(page, size)
        return routes.map { GetRouteDetailResult.from(it) }
    }
}
