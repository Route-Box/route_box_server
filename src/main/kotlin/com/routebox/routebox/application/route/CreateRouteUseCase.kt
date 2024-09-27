package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.CreateRouteCommand
import com.routebox.routebox.application.route.dto.CreateRouteResult
import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateRouteUseCase(
    private val routeService: RouteService,
    private val userService: UserService,
) {
    /**
     * 루트 등록
     *
     * @param command user id, startTime, endTime
     * @return 루트 id
     * @throws
     */
    @Transactional
    operator fun invoke(command: CreateRouteCommand): CreateRouteResult {
        val user = userService.getUserById(command.userId)
        val route = routeService.createRoute(user = user, startTime = command.startTime, endTime = command.endTime)
        return CreateRouteResult.from(route)
    }
}
