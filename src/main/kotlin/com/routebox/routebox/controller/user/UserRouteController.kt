package com.routebox.routebox.controller.user

import com.routebox.routebox.application.user.GetUserRouteUseCase
import com.routebox.routebox.controller.user.dto.GetUserRoutesResponse
import com.routebox.routebox.controller.user.dto.UserRouteResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class UserRouteController(
    private val getUserRouteUseCase: GetUserRouteUseCase,
) {
    @Operation(
        summary = "사용자 루트 목록 조회",
        description = "특정 사용자가 만든 루트 정보를 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/users/{userId}/routes")
    fun getUserRoutes(@AuthenticationPrincipal principal: UserPrincipal): GetUserRoutesResponse {
        val routeResponse = getUserRouteUseCase(userId = principal.userId)
        return GetUserRoutesResponse.from(routeResponse.map { UserRouteResponse.from(it) })
    }
}
