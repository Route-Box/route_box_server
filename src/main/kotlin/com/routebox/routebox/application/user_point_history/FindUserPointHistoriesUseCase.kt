package com.routebox.routebox.application.user_point_history

import com.routebox.routebox.controller.user_point_history.dto.UserPointHistoryResponse
import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user_point_history.UserPointHistoryService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class FindUserPointHistoriesUseCase(
    private val userPointHistoryService: UserPointHistoryService,
    private val routeService: RouteService,
) {
    operator fun invoke(query: Query): Page<UserPointHistoryResponse> {
        val pointHistories = userPointHistoryService.findByUserId(query.userId, query.page, query.pageSize)
        val routeIds = pointHistories.map { it.routeId }.filterNotNull().distinct()
        val routeMap = routeService.findRoutesByIds(routeIds).associateBy { it.id }
        return pointHistories.map { history ->
            UserPointHistoryResponse.from(
                userPointHistory = history,
                route = history.routeId?.let { routeMap[it] },
            )
        }
    }

    data class Query(
        val userId: Long,
        val page: Int,
        val pageSize: Int,
    )
}
