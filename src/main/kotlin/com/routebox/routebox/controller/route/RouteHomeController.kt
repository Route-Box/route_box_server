package com.routebox.routebox.controller.route

import com.routebox.routebox.application.popular_route.GetPopularRoutesUseCase
import com.routebox.routebox.application.recommended_route.GetRecommendedRoutesUseCase
import com.routebox.routebox.controller.route.dto.home.GetPopularRoutesResponse
import com.routebox.routebox.controller.route.dto.home.GetRecommendedRoutesResponse
import com.routebox.routebox.controller.route.dto.home.PopularRouteDto
import com.routebox.routebox.controller.route.dto.home.RecommendedRouteDto
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "홈 - 루트 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteHomeController(
    private val getRecommendedRoutesUseCase: GetRecommendedRoutesUseCase,
    private val getPopularRoutesUseCase: GetPopularRoutesUseCase,
) {
    @Operation(
        summary = "추천 루트 조회",
        description = "추천 루트 조회",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/routes/recommend")
    fun getRecommendedRoutes(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): GetRecommendedRoutesResponse {
        val routes = getRecommendedRoutesUseCase()
        return GetRecommendedRoutesResponse.from(
            comment = routes.firstOrNull()?.commonComment,
            routes = routes.map {
                RecommendedRouteDto.from(
                    it.id,
                    it.name,
                    it.description,
                    it.routeImageUrl,
                )
            },
        )
    }

    @Operation(
        summary = "인기 루트 조회",
        description = "인기 루트 조회",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/routes/popular")
    fun getPopularRoutes(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): GetPopularRoutesResponse {
        val routes = getPopularRoutesUseCase().map {
            PopularRouteDto.from(
                it.id,
                it.name,
            )
        }
        return GetPopularRoutesResponse.from(routes)
    }
}
