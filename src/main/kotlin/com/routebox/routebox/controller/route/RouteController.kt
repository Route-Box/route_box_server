package com.routebox.routebox.controller.route

import com.routebox.routebox.application.route.GetLatestRoutesUseCase
import com.routebox.routebox.application.route.GetRouteDetailUseCase
import com.routebox.routebox.controller.route.dto.GetLatestRoutesResponse
import com.routebox.routebox.controller.route.dto.RouteResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "루트 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteController(
    private val getLatestRoutesUseCase: GetLatestRoutesUseCase,
    private val getRouteDetailUseCase: GetRouteDetailUseCase,
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
        summary = "루트 상세 조회",
        description = "루트 상세정보 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", description = "[3002] 존재하지 않는 루트 id", content = [Content()]),
    )
    @GetMapping("/v1/routes/{routeId}")
    fun getRouteDetail(
        @RequestParam routeId: Long,
    ): RouteResponse {
        val routeResponse = getRouteDetailUseCase(routeId)
        return RouteResponse.from(routeResponse)
    }
}
