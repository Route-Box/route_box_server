package com.routebox.routebox.controller.user

import com.routebox.routebox.application.user.CheckNicknameAvailabilityUseCase
import com.routebox.routebox.application.user.UpdateUserInfoUseCase
import com.routebox.routebox.application.user.dto.UpdateUserInfoCommand
import com.routebox.routebox.controller.user.dto.CheckNicknameAvailabilityResponse
import com.routebox.routebox.controller.user.dto.UpdateUserInfoRequest
import com.routebox.routebox.controller.user.dto.UserResponse
import com.routebox.routebox.domain.validation.Nickname
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class UserController(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val checkNicknameAvailabilityUseCase: CheckNicknameAvailabilityUseCase,
) {
    @Operation(
        summary = "닉네임 이용 가능 여부 확인",
        description = "닉네임의 이용 가능 여부를 확인합니다. 현재 사용중인 유저가 없다면 이용 가능한 닉네임입니다.",
    )
    @GetMapping("/v1/users/nickname/{nickname}/availability")
    fun checkNicknameAvailability(
        @PathVariable @Nickname @Parameter(
            description = "이용 가능 여부를 확인할 닉네임. 닉네임은 한글, 영문, 숫자로 이루어진, 특수문자와 공백을 제외한 2~8 글자여야 합니다.",
            example = "고작가",
        ) nickname: String,
    ): CheckNicknameAvailabilityResponse {
        val isAvailable = checkNicknameAvailabilityUseCase(nickname)
        return CheckNicknameAvailabilityResponse(nickname, isAvailable)
    }

    @Operation(
        summary = "내 정보 수정",
        description = "<p>내 정보(닉네임, 성별, 생일 등)를 수정합니다." +
            "<p>Request body를 통해 전달된 항목들만 수정되며, request body에 존재하지 않거나 <code>null</code>로 설정된 항목들은 수정되지 않습니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "409", description = "[3002] 변경하려고 하는 닉네임이 이미 사용중인 경우", content = [Content()]),
    )
    @PatchMapping("/v1/users/me")
    fun updateUserInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody @Valid request: UpdateUserInfoRequest,
    ): UserResponse {
        val updateUserInfo = updateUserInfoUseCase(
            UpdateUserInfoCommand(
                id = userPrincipal.userId,
                nickname = request.nickname,
                gender = request.gender,
                birthDay = request.birthDay,
                introduction = request.introduction,
            ),
        )
        return UserResponse.from(updateUserInfo)
    }
}
