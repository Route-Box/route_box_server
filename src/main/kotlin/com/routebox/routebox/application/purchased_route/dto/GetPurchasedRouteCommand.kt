package com.routebox.routebox.application.purchased_route.dto

data class GetPurchasedRouteCommand(
    val requesterId: Long,
    val purchasedRouteId: Long,
)
