package com.routebox.routebox.application.user

import com.routebox.routebox.application.user.dto.GetUserProfileResult
import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetUserProfileUseCase(
    private val userService: UserService,
    private val routeService: RouteService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): GetUserProfileResult {
        val user = userService.getUserById(userId)
        val numOfRoutes = routeService.countRoutesByUserId(userId)
        return GetUserProfileResult.from(user, numOfRoutes)
    }
}
