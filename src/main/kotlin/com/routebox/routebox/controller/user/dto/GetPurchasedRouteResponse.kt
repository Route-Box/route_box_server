package com.routebox.routebox.controller.user.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetPurchasedRouteResponse(
    @Schema(description = "구매한 루트 목록")
    val routes: List<PurchasedRouteResponse>,
)
