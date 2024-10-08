package com.routebox.routebox.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.ErrorResponse
import com.routebox.routebox.logger.Logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    /**
     * 인증이 필요한 endpoint에 대해 인증되지 않았을 때 동작하는 handler.
     *
     * @param request                 that resulted in an `AuthenticationException`
     * @param response                so that the user agent can begin authentication
     * @param authenticationException that caused the invocation
     */
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException,
    ) {
        Logger.warn("JwtAuthenticationEntryPoint.commence() ex=${authenticationException.message}")

        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "utf-8"
        response.writer.write(
            ObjectMapper().writeValueAsString(
                ErrorResponse(
                    CustomExceptionType.ACCESS_DENIED.code,
                    "${CustomExceptionType.ACCESS_DENIED.message} ${authenticationException.message}",
                ),
            ),
        )
    }
}
