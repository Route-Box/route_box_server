package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.DeleteRouteActivityCommand
import com.routebox.routebox.application.route.dto.DeleteRouteActivityResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteRouteActivityUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 활동 삭제
     *
     * @param command activityId
     * @return activityId
     * @throws
     */
    @Transactional
    operator fun invoke(command: DeleteRouteActivityCommand): DeleteRouteActivityResult {
        // TODO: 유저 검증
        routeService.deleteRouteActivity(command.activityId)
        return DeleteRouteActivityResult(command.activityId)
    }
}
