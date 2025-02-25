package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.CreateRoutePointCommand
import com.routebox.routebox.application.route.dto.CreateRoutePointResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

// TODO: points 배열로 받는거 적용되면 삭제 필요
@Component
class CreateRoutePointLegacyUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 위치 기록
     *
     * @param command user id, route id, latitude, longitude, point order
     * @return 루트 point id
     * @throws
     */
    @Transactional
    operator fun invoke(command: CreateRoutePointCommand): CreateRoutePointResult {
        // TODO: 루트 소유자와 요청자가 같은지 확인
        val routePoint = routeService.createRoutePoint(
            routeId = command.routeId,
            latitude = command.latitude,
            longitude = command.longitude,
            recordAt = command.recordAt,
        )
        return CreateRoutePointResult.from(routePoint)
    }
}
