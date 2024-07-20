package com.routebox.routebox.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
class SecurityConfig {
    companion object {
        // Authentication white paths (HTTP method 상관 X)
        private val AUTH_WHITE_PATHS = listOf(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/health",
        )

        // Authentication white list (특정 endpoint, HTTP method에 대해서만)
        private val AUTH_WHITE_LIST = mapOf(
            "/api/v1/auth/login/kakao" to HttpMethod.POST,
        )

        @Bean
        fun securityFilterChain(
            httpSecurity: HttpSecurity,
            jwtAccessDeniedHandler: JwtAccessDeniedHandler,
            jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
            jwtAuthenticationFilter: JwtAuthenticationFilter,
            jwtExceptionFilter: JwtExceptionFilter,
        ): SecurityFilterChain {
            return httpSecurity
                .csrf { it.disable() }
                .httpBasic { it.disable() }
                .formLogin { it.disable() }
                .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .cors { corsConfigurer ->
                    val corsConfigSrc = CorsConfigurationSource {
                        val corsConfig = CorsConfiguration()
                        corsConfig.allowCredentials = true
                        corsConfig.allowedOrigins = listOf(
                            // TODO: 웹 서비스 배포 후 CORS 정책 추가 필요
                            "http://localhost*",
                        )
                        corsConfig.allowedMethods = listOf(
                            HttpMethod.GET.name(),
                            HttpMethod.POST.name(),
                            HttpMethod.PUT.name(),
                            HttpMethod.DELETE.name(),
                            HttpMethod.PATCH.name(),
                            HttpMethod.OPTIONS.name(),
                        )
                        corsConfig.allowedHeaders = listOf("*")
                        corsConfig.exposedHeaders = listOf("*")
                        return@CorsConfigurationSource corsConfig
                    }
                    corsConfigurer.configurationSource(corsConfigSrc)
                }
                .authorizeHttpRequests { auth ->
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    AUTH_WHITE_PATHS.forEach { authWhitePath -> auth.requestMatchers(authWhitePath).permitAll() }
                    AUTH_WHITE_LIST.forEach { (path: String, httpMethod: HttpMethod) ->
                        auth.requestMatchers(httpMethod, path).permitAll()
                    }
                    auth.anyRequest().authenticated()
                }
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter.javaClass)
                .exceptionHandling { exceptionHandlingConfigurer ->
                    exceptionHandlingConfigurer
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                }
                .build()
        }
    }
}