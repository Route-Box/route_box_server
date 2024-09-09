package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.ActivityResult
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetRouteActivityDetailUseCase(
    private val routeService: RouteService,
) {
    /**
     * 루트 활동 상세 조회.
     *
     * @param command activity id
     * @return 루트 활동 상세 정보
     * @throws // TODO
     */
    @Transactional(readOnly = true)
    operator fun invoke(id: Long): ActivityResult {
        val activity = routeService.findRouteActivityById(id).let {
            it ?: throw IllegalArgumentException("Route activity not found")
        }

        return ActivityResult.from(activity)
    }
}
