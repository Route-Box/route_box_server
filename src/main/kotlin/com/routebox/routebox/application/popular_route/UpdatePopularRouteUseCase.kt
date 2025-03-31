package com.routebox.routebox.application.popular_route

import com.routebox.routebox.domain.popular_route.PopularRouteService
import com.routebox.routebox.domain.purchased_route.PurchasedRouteService
import org.springframework.stereotype.Component

@Component
class UpdatePopularRouteUseCase(
    private val popularRouteService: PopularRouteService,
    private val purchasedRouteService: PurchasedRouteService,
) {
    operator fun invoke(): Int {
        // 많이 담은 루트 조회
        val popularRoutes = purchasedRouteService.getPurchasedRouteCountByRoute()

        // 인기 루트에 저장
        popularRouteService.createPopularRoutes(popularRoutes)
        return 0
    }
}
