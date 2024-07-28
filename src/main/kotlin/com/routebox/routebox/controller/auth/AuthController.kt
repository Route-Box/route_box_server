package com.routebox.routebox.controller.auth

import com.routebox.routebox.application.auth.AppleLoginUseCase
import com.routebox.routebox.application.auth.KakaoLoginUseCase
import com.routebox.routebox.application.auth.RefreshTokensUseCase
import com.routebox.routebox.application.auth.dto.AppleLoginCommand
import com.routebox.routebox.application.auth.dto.KakaoLoginCommand
import com.routebox.routebox.controller.auth.dto.AppleLoginRequest
import com.routebox.routebox.controller.auth.dto.KakaoLoginRequest
import com.routebox.routebox.controller.auth.dto.LoginResponse
import com.routebox.routebox.controller.auth.dto.RefreshTokensRequest
import com.routebox.routebox.controller.auth.dto.RefreshTokensResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증 관련 API")
@RequestMapping("/api")
@RestController
class AuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val appleLoginUseCase: AppleLoginUseCase,
    private val refreshTokensUseCase: RefreshTokensUseCase,
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
        val result = kakaoLoginUseCase(KakaoLoginCommand(request.kakaoAccessToken))
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
        val result = appleLoginUseCase(AppleLoginCommand(request.idToken))
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
}
