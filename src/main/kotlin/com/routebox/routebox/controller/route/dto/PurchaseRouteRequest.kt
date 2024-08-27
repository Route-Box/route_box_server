package com.routebox.routebox.controller.route.dto

import com.routebox.routebox.domain.purchased_route.constant.RoutePaymentMethod
import io.swagger.v3.oas.annotations.media.Schema

data class PurchaseRouteRequest(
    @Schema(description = "결제(구매) 수단")
    val paymentMethod: RoutePaymentMethod,
)
