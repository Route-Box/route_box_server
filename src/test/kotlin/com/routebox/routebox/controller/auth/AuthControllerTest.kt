package com.routebox.routebox.controller.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.application.auth.KakaoLoginCommand
import com.routebox.routebox.application.auth.KakaoLoginResult
import com.routebox.routebox.application.auth.KakaoLoginUseCase
import com.routebox.routebox.config.ControllerTestConfig
import com.routebox.routebox.security.JwtInfo
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
    lateinit var kakaoLoginUseCase: KakaoLoginUseCase

    @Test
    fun `카카오에서 발급받은 access token이 주어지고, 주어진 token으로 로그인한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        val expectedResult = KakaoLoginResult(
            isNew = false,
            accessToken = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now()),
            refreshToken = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now()),
        )
        given(kakaoLoginUseCase.invoke(KakaoLoginCommand(kakaoAccessToken))).willReturn(expectedResult)

        // when & then
        mvc.perform(
            post("/api/v1/auth/login/kakao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(KakaoLoginRequest(kakaoAccessToken))),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.result.isNew").value(expectedResult.isNew))
            .andExpect(jsonPath("$.result.accessToken.token").value(expectedResult.accessToken.token))
            .andExpect(jsonPath("$.result.refreshToken.token").value(expectedResult.refreshToken.token))
        then(kakaoLoginUseCase).should().invoke(KakaoLoginCommand(kakaoAccessToken))
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(kakaoLoginUseCase).shouldHaveNoMoreInteractions()
    }
}