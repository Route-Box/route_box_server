package com.routebox.routebox.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.ErrorResponse
import com.routebox.routebox.logger.Logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {
    /**
     * Endpoint에 대해 접근 권한이 존재하지 않을 때 동작하는 handler.
     *
     * @param request               that resulted in an `AccessDeniedException`
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     */
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        Logger.warn("JwtAccessDeniedHandler.handle() ex=${accessDeniedException.message}")

        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "utf-8"
        response.writer.write(
            ObjectMapper().writeValueAsString(
                ErrorResponse(
                    CustomExceptionType.ACCESS_DENIED.code,
                    CustomExceptionType.ACCESS_DENIED.message,
                ),
            ),
        )
    }
}
