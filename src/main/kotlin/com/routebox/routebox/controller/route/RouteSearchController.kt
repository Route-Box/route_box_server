package com.routebox.routebox.controller.route

import com.routebox.routebox.application.route.SearchRoutesUseCase
import com.routebox.routebox.application.route.dto.SearchCommand
import com.routebox.routebox.controller.route.dto.RouteSortBy
import com.routebox.routebox.controller.route.dto.SearchFilters
import com.routebox.routebox.controller.route.dto.SearchRouteDto
import com.routebox.routebox.controller.route.dto.SearchRoutesResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "루트 검색 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class RouteSearchController(
    private val searchRoutesUseCase: SearchRoutesUseCase,
) {
    @Operation(
        summary = "루트 검색",
        description = "검색어, 검색 필터, 정렬 등을 이용한 루트 검색",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
    )
    @Parameters(
        Parameter(name = "sortBy", description = "정렬 기준 (NEWEST, OLDEST, POPULAR, COMMENTS)"),
        Parameter(name = "whoWith", description = "대상 (예: 친구, 가족)"),
        Parameter(name = "numberOfPeople", description = "인원수 (예: 2, 3)"),
        Parameter(name = "numberOfDays", description = "머무는 기간 (일 수)"),
        Parameter(name = "style", description = "루트 스타일 (예: 모험, 휴식)"),
        Parameter(name = "transportation", description = "이동수단 (예: 자동차, 자전거)"),
    )
    @GetMapping("/v1/search")
    fun searchRoutes(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) query: String? = null,
        @RequestParam(required = false) sortBy: RouteSortBy = RouteSortBy.NEWEST,
        @RequestParam(required = false) whoWith: List<String> = emptyList(),
        @RequestParam(required = false) numberOfPeople: List<Int> = emptyList(),
        @RequestParam(required = false) numberOfDays: List<String> = emptyList(),
        @RequestParam(required = false) style: List<String> = emptyList(),
        @RequestParam(required = false) transportation: List<String> = emptyList(),
    ): SearchRoutesResponse {
        val filters = SearchFilters.from(whoWith, numberOfPeople, numberOfDays, style, transportation)
        val request = SearchCommand(page, size, filters, query, sortBy)
        return SearchRoutesResponse.from(searchRoutesUseCase(request).map { SearchRouteDto.from(it) })
    }
}
