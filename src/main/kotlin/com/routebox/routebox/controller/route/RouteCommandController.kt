package com.routebox.routebox.controller.route

import com.routebox.routebox.application.route.CreateRouteUseCase
import com.routebox.routebox.controller.route.dto.CreateRouteRequest
import com.routebox.routebox.controller.route.dto.CreateRouteResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "내루트 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteCommandController(
    private val createRouteUseCase: CreateRouteUseCase,
) {
    @Operation(
        summary = "루트 생성 (루트 기록 시작)",
        description = "루트 기록 시작일시, 종료일시 등록",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @PostMapping("/v1/routes/start")
    fun createRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: CreateRouteRequest,
    ): CreateRouteResponse {
        val routeResponse = createRouteUseCase(request.toCommand(userId = userPrincipal.userId))
        return CreateRouteResponse.from(routeResponse.routeId)
    }
}
