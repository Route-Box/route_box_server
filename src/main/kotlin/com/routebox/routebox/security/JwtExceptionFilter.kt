package com.routebox.routebox.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.routebox.routebox.exception.CustomExceptionType
import com.routebox.routebox.exception.ErrorResponse
import com.routebox.routebox.exception.security.InvalidTokenException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * `JwtAuthenticationFilter`에서 발생하는 에러를 처리하기 위한 filter
 *
 * @see JwtAuthenticationFilter
 */
@Component
class JwtExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: InvalidTokenException) {
            setErrorResponse(CustomExceptionType.INVALID_TOKEN, response)
        }
    }

    /**
     * Exception 정보를 입력받아 응답할 error response를 설정한다.
     *
     * @param exceptionType exception type
     * @param response      HttpServletResponse 객체
     */
    private fun setErrorResponse(
        exceptionType: CustomExceptionType,
        response: HttpServletResponse,
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.characterEncoding = "utf-8"
        response.contentType = "application/json; charset=UTF-8"
        val errorResponse = ErrorResponse(exceptionType.code, exceptionType.message)
        ObjectMapper().writeValue(response.outputStream, errorResponse)
    }
}
