package com.routebox.routebox.application.purchased_route

import com.routebox.routebox.application.purchased_route.dto.GetPurchasedRouteCommand
import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import com.routebox.routebox.domain.purchased_route.PurchasedRouteService
import com.routebox.routebox.exception.purchased_route.RouteNotPurchasedException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetPurchasedRouteUseCase(
    private val purchasedRouteService: PurchasedRouteService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(command: GetPurchasedRouteCommand): PurchasedRoute {
        val purchasedRoute = purchasedRouteService.getPurchasedRouteById(command.purchasedRouteId)
        if (purchasedRoute.buyer.id != command.requesterId) {
            throw RouteNotPurchasedException()
        }
        return purchasedRoute
    }
}
