package com.routebox.routebox.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtManager: JwtManager,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    companion object {
        private const val TOKEN_TYPE_BEARER_PREFIX = "Bearer "
    }

    /**
     * 모든 API 요청마다 작동하여, access token을 확인한다.
     * 유효한 token이 있는 경우 token을 parsing해서 사용자 정보를 읽고 SecurityContext에 사용자 정보를 저장한다.
     *
     * @param request     request 객체
     * @param response    response 객체
     * @param filterChain FilterChain 객체
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val accessToken = getAccessToken(request)
        if (!accessToken.isNullOrBlank()) {
            try {
                jwtManager.validate(accessToken)
                val userId = jwtManager.getSubjectFromToken(accessToken)
                val userDetails = userDetailsService.loadUserByUsername(userId)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (ignored: Exception) {
                // 인증 권한 설정 중 에러가 발생하면 권한을 부여하지 않고 다음 단계로 진행
            }
        }
        filterChain.doFilter(request, response)
    }

    /**
     * Request의 header에서 token을 읽어온다.
     *
     * @param request Request 객체
     * @return Header에서 추출한 token
     */
    fun getAccessToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_TYPE_BEARER_PREFIX)) {
            return null
        }
        return authorizationHeader.substring(TOKEN_TYPE_BEARER_PREFIX.length)
    }
}
