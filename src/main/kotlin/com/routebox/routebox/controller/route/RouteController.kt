package com.routebox.routebox.controller.route

import com.routebox.routebox.application.route.GetLatestRoutesUseCase
import com.routebox.routebox.application.route.GetRouteDetailUseCase
import com.routebox.routebox.application.route.GetRouteDetailWithActivitiesUseCase
import com.routebox.routebox.application.route.PurchaseRouteUseCase
import com.routebox.routebox.application.route.dto.PurchaseRouteCommand
import com.routebox.routebox.controller.route.dto.GetLatestRoutesResponse
import com.routebox.routebox.controller.route.dto.PurchaseRouteRequest
import com.routebox.routebox.controller.route.dto.RouteDetailResponse
import com.routebox.routebox.controller.route.dto.RouteResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "루트 탐색 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteController(
    private val getLatestRoutesUseCase: GetLatestRoutesUseCase,
    private val getRouteDetailUseCase: GetRouteDetailUseCase,
    private val getRouteDetailWithActivitiesUseCase: GetRouteDetailWithActivitiesUseCase,
    private val purchaseRouteUseCase: PurchaseRouteUseCase,
) {
    @Operation(
        summary = "루트 탐색",
        description = "최신순으로 루트 목록 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @GetMapping("/v1/routes")
    fun getLatestRoutes(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): GetLatestRoutesResponse {
        val routeResponses = getLatestRoutesUseCase(page, size).map { RouteResponse.from(it) }
        return GetLatestRoutesResponse.from(routeResponses)
    }

    @Operation(
        summary = "루트 단건 조회",
        description = "루트 단건 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", description = "[3002] 존재하지 않는 루트 id", content = [Content()]),
    )
    @GetMapping("/v1/routes/{routeId}")
    fun getRouteDetail(
        @PathVariable routeId: Long,
    ): RouteResponse {
        val routeResponse = getRouteDetailUseCase(routeId)
        return RouteResponse.from(routeResponse)
    }

    @Operation(
        summary = "구매한 루트, 내 루트 상세 조회",
        description = "루트 경로, 활동이 포함된 상세 정보",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", description = "[3002] 존재하지 않는 루트 id", content = [Content()]),
    )
    @GetMapping("/v1/routes/{routeId}/detail")
    fun getRouteDetailWithActivities(
        @PathVariable routeId: Long,
    ): RouteDetailResponse {
        val routeResponse = getRouteDetailWithActivitiesUseCase(routeId)
        return RouteDetailResponse.from(routeResponse)
    }

    @Operation(
        summary = "루트 구매하기",
        description = "<p>루트를 구매합니다. 루트는 쿠폰과 포인트를 사용하여 구매할 수 있습니다." +
            "<p>쿠폰을 사용하여 루트를 구매할 경우, 이용 종료일이 얼마 남지 않은 쿠폰이 자동으로 사용됩니다" +
            "<p>포인트를 사용하여 루트를 구매하는 기능은 현재 미구현 상태입니다. (추후 구현 예정)",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "204"),
        ApiResponse(responseCode = "400", description = "[3003] (쿠폰으로 구매 시) 이용 가능한 쿠폰이 없는 경우", content = [Content()]),
    )
    @PostMapping("/v1/routes/{routeId}/purchase")
    fun purchaseRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @RequestBody request: PurchaseRouteRequest,
    ): ResponseEntity<Unit> {
        purchaseRouteUseCase(
            PurchaseRouteCommand(
                buyerId = userPrincipal.userId,
                routeId = routeId,
                paymentMethod = request.paymentMethod,
            ),
        )
        return ResponseEntity.noContent().build()
    }
}
