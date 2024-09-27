package com.routebox.routebox.application.purchased_route

import com.routebox.routebox.application.purchased_route.dto.FindLatestPurchasedRoutesCommand
import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import com.routebox.routebox.domain.purchased_route.PurchasedRouteService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FindLatestPurchasedRoutesUseCase(private val purchasedRouteService: PurchasedRouteService) {
    @Transactional(readOnly = true)
    operator fun invoke(command: FindLatestPurchasedRoutesCommand): Page<PurchasedRoute> =
        purchasedRouteService.findLatestPurchasedRoutesByBuyer(
            buyerId = command.requesterId,
            page = command.page,
            pageSize = command.pageSize,
        )
}
