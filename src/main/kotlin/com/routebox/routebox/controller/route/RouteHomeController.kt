package com.routebox.routebox.controller.route

import com.routebox.routebox.controller.route.dto.GetPopularRoutesResponse
import com.routebox.routebox.controller.route.dto.GetRecommendedRoutesResponse
import com.routebox.routebox.controller.route.dto.PopularRouteDto
import com.routebox.routebox.controller.route.dto.RecommendRouteDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "홈 - 루트 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteHomeController {
    @Operation(
        summary = "추천 루트 조회",
        description = "추천 루트 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @GetMapping("/v1/routes/recommend")
    fun getRecommendedRoutes(): GetRecommendedRoutesResponse {
        val comment = "8월엔 여기로 여행 어때요?"
        val mockData = listOf(
            RecommendRouteDto.from(1, "경주 200% 즐기는 법", "경주 200% 즐기는 법", "https://routebox-resources.s3.ap-northeast-2.amazonaws.com/image/1.jpg"),
            RecommendRouteDto.from(2, "대구 먹방 여행", "대구 200% 즐기는 법", "https://routebox-resources.s3.ap-northeast-2.amazonaws.com/image/2.jpg"),
            RecommendRouteDto.from(3, "대전 빵 여행", "대전 200% 즐기는 법", "https://routebox-resources.s3.ap-northeast-2.amazonaws.com/image/3.jpg"),
        )
        return GetRecommendedRoutesResponse.from(comment, mockData)
    }

    @Operation(
        summary = "인기 루트 조회",
        description = "인기 루트 조회",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @GetMapping("/v1/routes/popular")
    fun getPopularRoutes(): GetPopularRoutesResponse {
        val mockData = listOf(
            PopularRouteDto(1, "8월에 꼭 가야하는 장소"),
            PopularRouteDto(2, "여자친구에게 칭찬 왕창 받은 데이트 코스"),
            PopularRouteDto(3, "친구들과 함께 떠나고 싶은 여행지"),
        )
        return GetPopularRoutesResponse.from(mockData)
    }
}
