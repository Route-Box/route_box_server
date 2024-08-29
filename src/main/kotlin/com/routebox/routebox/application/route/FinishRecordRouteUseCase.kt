package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.FinishRecordRouteCommand
import com.routebox.routebox.application.route.dto.FinishRecordRouteResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FinishRecordRouteUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 마무리
     *
     * @param command route id, name, description
     * @return 루트 정보
     * @throws
     */
    @Transactional
    operator fun invoke(command: FinishRecordRouteCommand): FinishRecordRouteResult {
        val route = routeService.finishRecordRoute(
            routeId = command.routeId,
            name = command.name,
            description = command.description,
        )
        return FinishRecordRouteResult.from(route)
    }
}
