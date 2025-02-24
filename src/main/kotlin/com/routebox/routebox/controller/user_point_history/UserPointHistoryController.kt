package com.routebox.routebox.controller.user_point_history

import com.routebox.routebox.application.user_point_history.FindUserPointHistoriesUseCase
import com.routebox.routebox.controller.user_point_history.dto.UserPointHistoryResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 포인트 이력 API")
@RestController
class UserPointHistoryController(
    private val findUserPointHistoriesUseCase: FindUserPointHistoriesUseCase,
) {
    @Operation(
        summary = "내 포인트 이력 조회",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/api/users/me/point-histories")
    fun findUserPointHistories(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): Page<UserPointHistoryResponse> = findUserPointHistoriesUseCase(
        FindUserPointHistoriesUseCase.Query(
            userId = userPrincipal.userId,
            page = page,
            pageSize = pageSize,
        ),
    )
}
