package com.routebox.routebox.controller.point

import com.routebox.routebox.application.user_point_history.AddUserPointHistoriesUseCase
import com.routebox.routebox.application.user_point_history.dto.AddUserPointHistoriesCommand
import com.routebox.routebox.controller.point.dto.AddUserPointRequest
import com.routebox.routebox.controller.point.dto.AddUserPointResponse
import com.routebox.routebox.domain.user.constant.UserPointTransactionType
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 포인트 API")
@RestController
class UserPointController(
    private val addUserPointHistoriesUseCase: AddUserPointHistoriesUseCase,
) {
    @Operation(
        summary = "포인트 충전",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/api/v1/users/me/points")
    fun addUserPoint(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: AddUserPointRequest,
    ): AddUserPointResponse {
        val point = addUserPointHistoriesUseCase(
            AddUserPointHistoriesCommand(
                userId = userPrincipal.userId,
                point = request.point,
                transactionType = UserPointTransactionType.CHARGE_BY_PAYMENT,
            ),
        )
        return AddUserPointResponse(point)
    }
}
