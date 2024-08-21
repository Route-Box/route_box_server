package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.UpdateRouteCommand
import com.routebox.routebox.application.route.dto.UpdateRouteResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UpdateRouteUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 수정
     *
     * @param command user id, route id, name, description,
     * @return 루트 정보
     * @throws
     */
    @Transactional
    operator fun invoke(command: UpdateRouteCommand): UpdateRouteResult {
        val route = routeService.updateRoute(
            routeId = command.routeId,
            name = command.name,
            description = command.description,
            whoWith = command.whoWith,
            numberOfPeople = command.numberOfPeople,
            numberOfDays = command.numberOfDays,
            style = command.style,
            transportation = command.transportation,
        )
        return UpdateRouteResult.from(route)
    }
}
