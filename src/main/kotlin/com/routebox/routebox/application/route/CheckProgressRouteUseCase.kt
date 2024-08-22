package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.CheckProgressRouteCommand
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CheckProgressRouteUseCase(
    private val routeService: RouteService,
) {
    /**
     * 기록중인 루트 여부 조회
     *
     * @param command user id
     * @return 루트 id
     * @throws
     */
    @Transactional(readOnly = true)
    operator fun invoke(command: CheckProgressRouteCommand): Long? {
        val route = routeService.getProgressRouteByUserId(command.userLocalTime, command.userId).let {
            it ?: return null
        }
        return route.id
    }
}
