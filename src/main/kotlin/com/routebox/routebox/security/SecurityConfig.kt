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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
            "/api/v1/auth/login/apple" to HttpMethod.POST,
            "/api/v1/auth/tokens/refresh" to HttpMethod.POST,
            "/api/v1/users/nickname/*/availability" to HttpMethod.GET,
            "/api/v1/notifications" to HttpMethod.GET,
            "/api/v1/notifications/unread" to HttpMethod.GET,
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
                .cors {
                    it.configurationSource(corsConfigurationSource())
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

        @Bean
        fun corsConfigurationSource(): CorsConfigurationSource {
            val corsConfig = CorsConfiguration()
            corsConfig.allowedOrigins = listOf(
                "https://api-dev.myroutebox.com",
                "https://www.myroutebox.com",
                "https://myroutebox.com",
                "http://localhost:8080",
                // For web
                "http://localhost:5173",
                "https://www.dev.myroutebox.com",
                "https://dev.d1a0dmxvzr7an0.amplifyapp.com",
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
            corsConfig.allowCredentials = true
            corsConfig.maxAge = 3600L

            val source = UrlBasedCorsConfigurationSource()
            source.registerCorsConfiguration("/**", corsConfig)
            return source
        }
    }
}
