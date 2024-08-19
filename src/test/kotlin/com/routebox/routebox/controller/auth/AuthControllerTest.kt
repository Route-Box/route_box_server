package com.routebox.routebox.controller.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.application.auth.OAuthLoginUseCase
import com.routebox.routebox.application.auth.RefreshTokensUseCase
import com.routebox.routebox.application.auth.dto.LoginResult
import com.routebox.routebox.application.auth.dto.OAuthLoginCommand
import com.routebox.routebox.config.ControllerTestConfig
import com.routebox.routebox.controller.auth.dto.AppleLoginRequest
import com.routebox.routebox.controller.auth.dto.KakaoLoginRequest
import com.routebox.routebox.controller.auth.dto.RefreshTokensRequest
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.security.JwtInfo
import org.apache.commons.lang3.RandomStringUtils
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@Import(ControllerTestConfig::class)
@WebMvcTest(controllers = [AuthController::class])
class AuthControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @MockBean
    lateinit var oAuthLoginUseCase: OAuthLoginUseCase

    @MockBean
    lateinit var refreshTokensUseCase: RefreshTokensUseCase

    @Test
    fun `카카오에서 발급받은 access token이 주어지고, 주어진 token으로 로그인한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        val expectedResult = LoginResult(
            isNew = false,
            loginType = LoginType.KAKAO,
            accessToken = JwtInfo(token = toString(), expiresAt = LocalDateTime.now()),
            refreshToken = JwtInfo(token = toString(), expiresAt = LocalDateTime.now()),
        )
        given(oAuthLoginUseCase.invoke(OAuthLoginCommand(LoginType.KAKAO, kakaoAccessToken))).willReturn(expectedResult)

        // when & then
        mvc.perform(
            post("/api/v1/auth/login/kakao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(KakaoLoginRequest(kakaoAccessToken))),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.isNew").value(expectedResult.isNew))
            .andExpect(jsonPath("$.loginType").value(expectedResult.loginType.toString()))
            .andExpect(jsonPath("$.accessToken.token").value(expectedResult.accessToken.token))
            .andExpect(jsonPath("$.refreshToken.token").value(expectedResult.refreshToken.token))
        then(oAuthLoginUseCase).should().invoke(OAuthLoginCommand(LoginType.KAKAO, kakaoAccessToken))
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    @Test
    fun `애플에서 발급받은 id token이 주어지고, 주어진 token으로 로그인한다`() {
        // given
        val appleIdToken = Random.toString()
        val expectedResult = LoginResult(
            isNew = false,
            loginType = LoginType.APPLE,
            accessToken = JwtInfo(token = toString(), expiresAt = LocalDateTime.now()),
            refreshToken = JwtInfo(token = toString(), expiresAt = LocalDateTime.now()),
        )
        given(oAuthLoginUseCase.invoke(OAuthLoginCommand(LoginType.APPLE, appleIdToken))).willReturn(expectedResult)

        // when & then
        mvc.perform(
            post("/api/v1/auth/login/apple")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AppleLoginRequest(appleIdToken))),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.isNew").value(expectedResult.isNew))
            .andExpect(jsonPath("$.loginType").value(expectedResult.loginType.toString()))
            .andExpect(jsonPath("$.accessToken.token").value(expectedResult.accessToken.token))
            .andExpect(jsonPath("$.refreshToken.token").value(expectedResult.refreshToken.token))
        then(oAuthLoginUseCase).should().invoke(OAuthLoginCommand(LoginType.APPLE, appleIdToken))
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    @Test
    fun `refresh token이 주어지고, 주어진 refresh token으로 새로운 access token과 refresh token을 발급한다`() {
        // given
        val refreshToken = RandomStringUtils.random(30)
        val expectedAccessTokenResult = JwtInfo(token = RandomStringUtils.random(10), expiresAt = LocalDateTime.now())
        val expectedRefreshTokenResult = JwtInfo(token = RandomStringUtils.random(10), expiresAt = LocalDateTime.now())
        given(refreshTokensUseCase.invoke(refreshToken))
            .willReturn(Pair(expectedAccessTokenResult, expectedRefreshTokenResult))

        // when & then
        mvc.perform(
            post("/api/v1/auth/tokens/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(RefreshTokensRequest(refreshToken))),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken.token").value(expectedAccessTokenResult.token))
            .andExpect(jsonPath("$.refreshToken.token").value(expectedRefreshTokenResult.token))
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(oAuthLoginUseCase).shouldHaveNoMoreInteractions()
        then(refreshTokensUseCase).shouldHaveNoMoreInteractions()
    }
}
