package com.routebox.routebox.controller.auth

import com.routebox.routebox.application.auth.OAuthLoginUseCase
import com.routebox.routebox.application.auth.RefreshTokensUseCase
import com.routebox.routebox.application.auth.WithdrawUseCase
import com.routebox.routebox.application.auth.dto.OAuthLoginCommand
import com.routebox.routebox.application.auth.dto.WithdrawCommand
import com.routebox.routebox.controller.auth.dto.AppleLoginRequest
import com.routebox.routebox.controller.auth.dto.KakaoLoginRequest
import com.routebox.routebox.controller.auth.dto.LoginResponse
import com.routebox.routebox.controller.auth.dto.RefreshTokensRequest
import com.routebox.routebox.controller.auth.dto.RefreshTokensResponse
import com.routebox.routebox.controller.auth.dto.WithdrawRequest
import com.routebox.routebox.controller.auth.dto.WithdrawResponse
import com.routebox.routebox.domain.auth.WithdrawalReasonType
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증 관련 API")
@RequestMapping("/api")
@RestController
class AuthController(
    private val oAuthLoginUseCase: OAuthLoginUseCase,
    private val refreshTokensUseCase: RefreshTokensUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) {
    @Operation(
        summary = "카카오 로그인",
        description = "카카오 계정으로 로그인합니다.",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", description = "[10000] Kakao API 서버로부터 오류를 응답받은 경우.", content = [Content()]),
    )
    @PostMapping("/v1/auth/login/kakao")
    fun kakaoLoginV1(@RequestBody @Valid request: KakaoLoginRequest): LoginResponse {
        val result = oAuthLoginUseCase(OAuthLoginCommand(loginType = LoginType.KAKAO, token = request.kakaoAccessToken))
        return LoginResponse.from(result)
    }

    @Operation(
        summary = "애플 로그인",
        description = "애플 계정으로 로그인합니다.",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", description = "[10200] Apple API 서버로부터 오류를 응답받은 경우.", content = [Content()]),
        ApiResponse(responseCode = "401", description = "[10201] Id token이 유효하지 않은 경우", content = [Content()]),
    )
    @PostMapping("/v1/auth/login/apple")
    fun appleLoginV1(@RequestBody @Valid request: AppleLoginRequest): LoginResponse {
        val result = oAuthLoginUseCase(OAuthLoginCommand(loginType = LoginType.APPLE, token = request.idToken))
        return LoginResponse.from(result)
    }

    @Operation(
        summary = "토큰 재발급",
        description = "<p>Refresh token으로 access token과 refresh token을 재발행합니다." +
            "<p>토큰 재발급에 사용된 refresh token은 만료됩니다.(일회용)",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "401", description = "[2002] 토큰이 만료되었거나 유효하지 않은 경우", content = [Content()]),
    )
    @PostMapping("/v1/auth/tokens/refresh")
    fun refreshTokensV1(@RequestBody @Valid request: RefreshTokensRequest): RefreshTokensResponse {
        val (accessToken, refreshToken) = refreshTokensUseCase(request.refreshToken)
        return RefreshTokensResponse(accessToken, refreshToken)
    }

    @Operation(
        summary = "회원 탈퇴",
        description = "<p>회원 탈퇴를 진행합니다.</p>" +
            "<p>탈퇴 시, 90일이 지난 후 데이터 삭제.</p>" +
            "<p>탈퇴 사유 Enum 값:</p>" +
            "<ul>" +
            "<li><b>LIMITED_ROUTE_OPTIONS</b>: 루트가 다양하지 않아서 유용하지 않음</li>" +
            "<li><b>DIFFICULT_ROUTE_RECORDING</b>: 여행 루트를 기록하기 어려움</li>" +
            "<li><b>APP_NOT_AS_EXPECTED</b>: 다운로드 시 기대한 내용과 앱이 다름</li>" +
            "<li><b>LOW_SERVICE_TRUST</b>: 서비스 운영의 신뢰도가 낮음</li>" +
            "<li><b>ETC</b>: 기타</li>" +
            "</ul>",
        security = [SecurityRequirement(name = "access-token")],
    )
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "401", description = "[2002] 토큰이 만료되었거나 유효하지 않은 경우", content = [Content()]),
    )
    @PostMapping("/v1/auth/withdrawal")
    fun withdraw(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody @Valid request: WithdrawRequest,
    ): WithdrawResponse {
        withdrawUseCase(WithdrawCommand(principal.userId, WithdrawalReasonType.fromString(request.reasonType), request.reasonDetail))
        return WithdrawResponse(principal.userId)
    }
}
