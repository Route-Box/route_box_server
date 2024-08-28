package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetLatestRoutesCommand
import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetLatestRoutesUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 최신순 조회
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 루트 목록
     */
    @Transactional(readOnly = true)
    operator fun invoke(command: GetLatestRoutesCommand): List<GetRouteDetailResult> {
        val routes = routeService.getLatestRoutes(command.userId, command.page, command.size)
        return routes.map { GetRouteDetailResult.from(it) }
    }
}
