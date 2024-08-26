package com.routebox.routebox.controller.user

import com.routebox.routebox.application.user.GetUserRouteUseCase
import com.routebox.routebox.controller.user.dto.GetPurchasedRouteResponse
import com.routebox.routebox.controller.user.dto.GetUserRoutesResponse
import com.routebox.routebox.controller.user.dto.PurchasedRouteResponse
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

    @Operation(
        summary = "구매한 루트 목록 조회",
        description = "로그인된 사용자의 구매한 루트 목록 조회 (미구현)",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/users/me/purchased-routes")
    fun getPurchasedRoutes(): GetPurchasedRouteResponse {
        // TODO: 로직 구현

        val dummy = listOf(
            PurchasedRouteResponse(
                routeId = 1,
                routeName = "민속촌 산책로",
                routeDescription = "민속촌 산책로",
                routeImageUrl = "https://routebox-resources.s3.ap-northeast-2.amazonaws.com/image/1.jpg",
                createdAt = "2024-08-31T00:00:00",
            ),
            PurchasedRouteResponse(
                routeId = 2,
                routeName = "서울 랜드마크 투어",
                routeDescription = null,
                routeImageUrl = null,
                createdAt = "2024-08-24T00:00:00",
            ),
            PurchasedRouteResponse(
                routeId = 3,
                routeName = "부산 맛집 탐방",
                routeDescription = "부산 맛집 탐방",
                routeImageUrl = "https://routebox-resources.s3.ap-northeast-2.amazonaws.com/image/2.jpg",
                createdAt = "2024-08-11T00:00:00",
            ),
            PurchasedRouteResponse(
                routeId = 4,
                routeName = "경복궁 루트",
                routeDescription = "경복궁 루트",
                routeImageUrl = "https://routebox-resources.s3.ap-northeast-2.amazonaws.com/image/3.jpg",
                createdAt = "2024-08-01T00:00:00",
            ),
        )

        val routes = GetPurchasedRouteResponse(
            routes = dummy,
        )
        return routes
    }
}
