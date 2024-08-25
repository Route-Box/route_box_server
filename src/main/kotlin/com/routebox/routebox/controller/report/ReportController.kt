package com.routebox.routebox.controller.report

import com.routebox.routebox.controller.report.dto.ReportResponse
import com.routebox.routebox.controller.report.dto.ReportRouteRequest
import com.routebox.routebox.controller.report.dto.ReportUserRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "신고 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class ReportController() {
    @Operation(
        summary = "루트 신고",
        description = "루트 신고하기",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/reports/route")
    fun reportRoute(
        @RequestBody request: ReportRouteRequest,
    ): ReportResponse {
        // TODO: 구현
        return ReportResponse(1L)
    }

    @Operation(
        summary = "사용자 신고",
        description = "사용자 신고하기",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/reports/user")
    fun reportRoute(
        @RequestBody request: ReportUserRequest,
    ): ReportResponse {
        // TODO: 구현
        return ReportResponse(1L)
    }
}
