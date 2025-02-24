package com.routebox.routebox.controller.user

import com.routebox.routebox.application.user.CheckNicknameAvailabilityUseCase
import com.routebox.routebox.application.user.GetUserProfileUseCase
import com.routebox.routebox.application.user.GetUserUseCase
import com.routebox.routebox.application.user.UpdateUserInfoUseCase
import com.routebox.routebox.controller.user.dto.CheckNicknameAvailabilityResponse
import com.routebox.routebox.controller.user.dto.UpdateUserInfoRequest
import com.routebox.routebox.controller.user.dto.UserProfileResponse
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
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 관련 API")
@RestController
@Validated
@RequestMapping("/api")
class UserController(
    private val getUserUseCase: GetUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val checkNicknameAvailabilityUseCase: CheckNicknameAvailabilityUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
) {
    @Operation(
        summary = "내 유저 정보 조회",
        description = "내 정보를 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/users/me")
    fun getMyInfo(@AuthenticationPrincipal principal: UserPrincipal): UserResponse = getUserUseCase(userId = principal.userId)

    @Operation(
        summary = "내 프로필 정보 조회",
        description = "<p><strong>내 루트 개수, 취향 정보 등 추가 예정 (미구현)</strong>" +
            "<p>내 프로필 정보를 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/users/me/profile")
    fun getMyProfile(@AuthenticationPrincipal principal: UserPrincipal): UserProfileResponse {
        val myProfile = getUserProfileUseCase(userId = principal.userId)
        return UserProfileResponse.fromResult(myProfile)
    }

    @Operation(
        summary = "유저 프로필 정보 조회",
        description = "<p><strong>유저가 작성한 루트 개수, 취향 정보 등 추가 예정 (미구현)</strong>" +
            "<p>특정 유저의 프로필 정보를 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/users/{userId}/profile")
    fun getUserProfile(
        @Parameter(description = "프로필 정보를 조회할 유저의 id", example = "5") @PathVariable userId: Long,
    ): UserProfileResponse {
        val userProfile = getUserProfileUseCase(userId)
        return UserProfileResponse.fromResult(userProfile)
    }

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
            "<p>요청 시 content-type은 <code>multipart/form-data</code>로 설정하여 요청해야 합니다." +
            "<p>Request body를 통해 전달된 항목들만 수정되며, 요청 데이터에 존재하지 않거나 <code>null</code>로 설정된 항목들은 수정되지 않습니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "409", description = "[3002] 변경하려고 하는 닉네임이 이미 사용중인 경우", content = [Content()]),
    )
    @PatchMapping("/v1/users/me", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateUserInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @ModelAttribute @Valid request: UpdateUserInfoRequest,
    ): UserResponse {
        val updateUserInfo = updateUserInfoUseCase(request.toCommand(userPrincipal.userId))
        return UserResponse.from(updateUserInfo)
    }
}
