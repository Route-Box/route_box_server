package com.routebox.routebox.config

import com.routebox.routebox.security.JwtAccessDeniedHandler
import com.routebox.routebox.security.JwtAuthenticationEntryPoint
import com.routebox.routebox.security.JwtAuthenticationFilter
import com.routebox.routebox.security.SecurityConfig
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(
    value = [
        SecurityConfig::class,
        JwtAccessDeniedHandler::class,
        JwtAuthenticationEntryPoint::class,
    ],
)
@TestConfiguration
class TestSecurityConfig {

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return TestJwtAuthenticationFilter() // 테스트용 필터를 반환
    }

    class TestJwtAuthenticationFilter : JwtAuthenticationFilter(mock(), mock()) {
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain,
        ) {
            filterChain.doFilter(request, response)
        }
    }
}
