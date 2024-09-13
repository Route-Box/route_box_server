package com.routebox.routebox.controller.purchased_route

import com.routebox.routebox.application.purchased_route.FindLatestPurchasedRoutesUseCase
import com.routebox.routebox.application.purchased_route.GetPurchasedRouteUseCase
import com.routebox.routebox.application.purchased_route.dto.FindLatestPurchasedRoutesCommand
import com.routebox.routebox.application.purchased_route.dto.GetPurchasedRouteCommand
import com.routebox.routebox.controller.purchased_route.dto.FindPurchasedRoutesResponse
import com.routebox.routebox.controller.purchased_route.dto.GetPurchasedRouteResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "담은(구매한) 루트 관련 API")
@RequestMapping("/api")
@RestController
class PurchasedRouteController(
    private val getPurchasedRouteUseCase: GetPurchasedRouteUseCase,
    private val findLatestPurchasedRoutesUseCase: FindLatestPurchasedRoutesUseCase,
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
    ): GetPurchasedRouteResponse {
        val purchasedRoute =
            getPurchasedRouteUseCase(GetPurchasedRouteCommand(requesterId = userPrincipal.userId, purchasedRouteId))
        return GetPurchasedRouteResponse.from(purchasedRoute)
    }

    @Operation(
        summary = "내가 구매한 루트 목록 조회",
        description = "내가 구매한 루트 목록을 조회합니다. 정렬 기준은 최근에 구매한 루트 순입니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/purchased-routes")
    fun findPurchasedRoutes(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam page: Int,
        @Parameter(description = "페이지 크기") @RequestParam pageSize: Int,
    ): Page<FindPurchasedRoutesResponse.PurchasedRouteResponse> {
        val purchasedRoutes =
            findLatestPurchasedRoutesUseCase(
                FindLatestPurchasedRoutesCommand(
                    requesterId = userPrincipal.userId,
                    page,
                    pageSize,
                ),
            )
        return purchasedRoutes.map { FindPurchasedRoutesResponse.PurchasedRouteResponse.from(it) }
    }
}
