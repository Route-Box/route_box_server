package com.routebox.routebox.controller.user

import com.routebox.routebox.application.user.UpdateUserInfoUseCase
import com.routebox.routebox.application.user.dto.UpdateUserInfoCommand
import com.routebox.routebox.controller.common.BaseResponse
import com.routebox.routebox.controller.user.dto.UpdateUserInfoRequest
import com.routebox.routebox.controller.user.dto.UserResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/api")
class UserController(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
) {
    @Operation(
        summary = "내 정보 수정",
        description = "<p>내 정보(닉네임, 성별, 생일 등)를 수정합니다." +
            "<p>Request body를 통해 전달된 항목들만 수정되며, request body에 존재하지 않거나 <code>null</code>로 설정된 항목들은 수정되지 않습니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "409", description = "[3002] 변경하려고 하는 닉네임이 이미 사용중인 경우", content = [Content()]),
        ],
    )
    @PatchMapping("/v1/users/me")
    fun updateUserInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: UpdateUserInfoRequest,
    ): BaseResponse<UserResponse> {
        val updateUserInfo = updateUserInfoUseCase(
            UpdateUserInfoCommand(
                id = userPrincipal.userId,
                nickname = request.nickname,
                gender = request.gender,
                birthDay = request.birthDay,
                introduction = request.introduction,
            ),
        )
        return BaseResponse.success(UserResponse.from(updateUserInfo))
    }
}
