package com.routebox.routebox.controller.auth

import com.routebox.routebox.application.auth.AppleLoginCommand
import com.routebox.routebox.application.auth.AppleLoginUseCase
import com.routebox.routebox.application.auth.KakaoLoginCommand
import com.routebox.routebox.application.auth.KakaoLoginUseCase
import com.routebox.routebox.controller.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class AuthController(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val appleLoginUseCase: AppleLoginUseCase,
) {
    @Operation(
        summary = "카카오 로그인",
        description = "카카오 계정으로 로그인합니다.",
    )
    @PostMapping("/v1/auth/login/kakao")
    fun kakaoLoginV1(@RequestBody @Valid request: KakaoLoginRequest): BaseResponse<LoginResponse> {
        val result = kakaoLoginUseCase(KakaoLoginCommand(request.kakaoAccessToken))
        return BaseResponse.success(LoginResponse.from(result))
    }

    @Operation(
        summary = "애플 로그인",
        description = "애플 계정으로 로그인합니다.",
    )
    @PostMapping("/v1/auth/login/apple")
    fun appleLoginV1(@RequestBody @Valid request: AppleLoginRequest): BaseResponse<LoginResponse> {
        val result = appleLoginUseCase(AppleLoginCommand(request.idToken))
        return BaseResponse.success(LoginResponse.from(result))
    }
}
