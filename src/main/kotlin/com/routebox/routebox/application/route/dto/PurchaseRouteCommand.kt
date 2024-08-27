package com.routebox.routebox.application.route.dto

import com.routebox.routebox.domain.purchased_route.constant.RoutePaymentMethod

data class PurchaseRouteCommand(
    val buyerId: Long,
    val routeId: Long,
    val paymentMethod: RoutePaymentMethod,
)
