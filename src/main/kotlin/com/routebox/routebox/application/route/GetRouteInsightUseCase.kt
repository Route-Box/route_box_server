package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetRouteInsightResult
import com.routebox.routebox.domain.purchased_route.PurchasedRouteService
import com.routebox.routebox.domain.route.RouteService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetRouteInsightUseCase(
    private val routeService: RouteService,
    private val purchasedRouteService: PurchasedRouteService,
) {
    /**
     * 루트 인사이트 조회
     *
     * @param command route id
     * @return 루트 인사이트 정보 (내루트 개수, 구매한 루트 개수, 댓글 개수)
     * @throws
     */
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): GetRouteInsightResult {
        val myRouteCount = routeService.getMyRouteCount(userId)
        val purchasedRouteCount = purchasedRouteService.getPurchasedRouteCount(userId)
        return GetRouteInsightResult(myRouteCount, purchasedRouteCount, 0)
    }
}
