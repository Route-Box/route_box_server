package com.routebox.routebox.application.user

import com.routebox.routebox.application.user.dto.GetUserRouteResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetUserRouteUseCase(
    private val routeService: RouteService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): List<GetUserRouteResult> {
        return routeService.getRoutesByUserId(userId).map { route ->
            GetUserRouteResult.from(route)
        }
    }
}
