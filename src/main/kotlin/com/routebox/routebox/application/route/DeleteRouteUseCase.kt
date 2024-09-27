package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.DeleteRouteCommand
import com.routebox.routebox.application.route.dto.DeleteRouteResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteRouteUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 삭제
     *
     * @param command routeId
     * @return routeId
     * @throws
     */
    @Transactional
    operator fun invoke(command: DeleteRouteCommand): DeleteRouteResult {
        // TODO: 유저 검증
        routeService.deleteRoute(command.routeId)
        return DeleteRouteResult(command.routeId)
    }
}
