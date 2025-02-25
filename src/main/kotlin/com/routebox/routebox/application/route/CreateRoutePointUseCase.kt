package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.CreateRoutePointCommand
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateRoutePointUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 위치 기록
     *
     * @param List<command> user id, route id, latitude, longitude, point order
     * @return 루트 point id
     * @throws
     */
    @Transactional
    operator fun invoke(commands: List<CreateRoutePointCommand>) {
        // TODO: 루트 소유자와 요청자가 같은지 확인
        routeService.createRoutePoints(commands)
        return
    }
}
