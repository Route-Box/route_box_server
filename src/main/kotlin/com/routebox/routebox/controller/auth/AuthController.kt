package com.routebox.routebox.controller.auth

import com.routebox.routebox.application.auth.KakaoLoginCommand
import com.routebox.routebox.application.auth.KakaoLoginUseCase
import com.routebox.routebox.controller.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class AuthController(private val kakaoLoginUseCase: KakaoLoginUseCase) {
    @Operation(
        summary = "카카오 로그인",
        description = "카카오 계정으로 로그인합니다.",
    )
    @PostMapping("/v1/auth/login/kakao")
    fun kakaoLogin(@RequestBody @Valid request: KakaoLoginRequest): BaseResponse<LoginResponse> {
        val result = kakaoLoginUseCase(KakaoLoginCommand(request.kakaoAccessToken))
        return BaseResponse.success(LoginResponse(result.isNew, result.accessToken, result.refreshToken))
    }
}
