package com.routebox.routebox.controller.purchased_route

import com.routebox.routebox.application.purchased_route.GetPurchasedRouteUseCase
import com.routebox.routebox.application.purchased_route.dto.GetPurchasedRouteCommand
import com.routebox.routebox.controller.purchased_route.dto.PurchasedRouteResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "담은(구매한) 루트 관련 API")
@RequestMapping("/api")
@RestController
class PurchasedRouteController(
    private val getPurchasedRouteUseCase: GetPurchasedRouteUseCase,
) {
    @Operation(
        summary = "구매한 루트 정보 상세조회",
        description = "구매한 루트 정보를 상세조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "403", description = "[3401] 내가 구매하지 않은 루트인 경우", content = [Content()]),
    )
    @GetMapping("/v1/purchased-routes/{purchasedRouteId}")
    fun getPurchasedRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @Parameter(description = "조회하고자 하는, 구매한 루트의 id") @PathVariable purchasedRouteId: Long,
    ): PurchasedRouteResponse {
        val purchasedRoute =
            getPurchasedRouteUseCase(GetPurchasedRouteCommand(requesterId = userPrincipal.userId, purchasedRouteId))
        return PurchasedRouteResponse.fromPurchasedRoute(purchasedRoute)
    }
}
