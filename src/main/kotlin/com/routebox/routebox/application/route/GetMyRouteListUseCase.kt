package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetMyRouteResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetMyRouteListUseCase(
    private val routeService: RouteService,
) {
    /**
     * 내루트 목록 조회
     *
     * @param command user id
     * @return 내루트 목록
     * @throws
     */
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): List<GetMyRouteResult> {
        val routes = routeService.getMyRoutes(userId)
        return routes.map { GetMyRouteResult.from(it) }
    }
}
