package com.routebox.routebox.controller.report

import com.routebox.routebox.application.comment_report.ReportCommentUseCase
import com.routebox.routebox.application.comment_report.dto.ReportCommentDto
import com.routebox.routebox.application.route_report.ReportRouteUseCase
import com.routebox.routebox.application.route_report.dto.ReportRouteCommand
import com.routebox.routebox.application.user_report.ReportUserUseCase
import com.routebox.routebox.application.user_report.dto.ReportUserCommand
import com.routebox.routebox.controller.report.dto.ReportCommentRequest
import com.routebox.routebox.controller.report.dto.ReportRouteRequest
import com.routebox.routebox.controller.report.dto.ReportRouteResponse
import com.routebox.routebox.controller.report.dto.ReportUserRequest
import com.routebox.routebox.controller.report.dto.ReportUserResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "신고 관련 API")
@RestController
@RequestMapping("/api")
class ReportController(
    private val reportUserUseCase: ReportUserUseCase,
    private val reportRouteUseCase: ReportRouteUseCase,
    private val reportCommentUseCase: ReportCommentUseCase,
) {
    @Operation(
        summary = "유저 신고",
        description = "유저 신고하기",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/reports/user")
    fun reportRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: ReportUserRequest,
    ): ReportUserResponse {
        val userReportId = reportUserUseCase(
            ReportUserCommand(
                reporterId = userPrincipal.userId,
                reportedUserId = request.userId,
            ),
        )
        return ReportUserResponse(userReportId)
    }

    @Operation(
        summary = "루트 신고",
        description = "루트 신고하기",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/reports/route")
    fun reportRoute(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: ReportRouteRequest,
    ): ReportRouteResponse {
        val routeReportId = reportRouteUseCase(
            ReportRouteCommand(
                reporterId = userPrincipal.userId,
                reportedRouteId = request.routeId,
                reasonTypes = request.reasonTypes,
                reasonDetail = request.reasonDetail,
            ),
        )
        return ReportRouteResponse(routeReportId)
    }

    @Operation(
        summary = "댓글 신고",
        description = "댓글 신고하기",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/v1/reports/comment")
    fun reportComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: ReportCommentRequest,
    ): ResponseEntity<String> {
        reportCommentUseCase(
            ReportCommentDto(
                reporterId = userPrincipal.userId,
                reportedCommentId = request.commentId,
            ),
        )

        return ResponseEntity.ok("댓글을 신고했습니다")
    }
}
