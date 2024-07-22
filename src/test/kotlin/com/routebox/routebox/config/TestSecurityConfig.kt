package com.routebox.routebox.config

import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.security.CustomUserDetailsService
import com.routebox.routebox.security.JwtAccessDeniedHandler
import com.routebox.routebox.security.JwtAuthenticationEntryPoint
import com.routebox.routebox.security.JwtAuthenticationFilter
import com.routebox.routebox.security.JwtExceptionFilter
import com.routebox.routebox.security.JwtManager
import com.routebox.routebox.security.SecurityConfig
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.kotlin.given
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.event.annotation.BeforeTestMethod
import java.time.LocalDate
import kotlin.random.Random

@Import(
    value = [
        SecurityConfig::class,
        JwtAccessDeniedHandler::class,
        JwtAuthenticationFilter::class,
        JwtAuthenticationEntryPoint::class,
        JwtExceptionFilter::class,
        CustomUserDetailsService::class,
        JwtManager::class,
    ],
)
@TestConfiguration
class TestSecurityConfig {
    @MockBean
    lateinit var userService: UserService

    @BeforeTestMethod
    fun securitySetUp() {
        given(userService.getUserById(anyLong())).willReturn(
            User(
                id = Random.nextLong(1, 10000),
                loginType = LoginType.KAKAO,
                socialLoginUid = Random.toString(),
                nickname = Random.toString(),
                gender = Gender.PRIVATE,
                birthDay = LocalDate.of(2024, 1, 1),
            ),
        )
    }
}
