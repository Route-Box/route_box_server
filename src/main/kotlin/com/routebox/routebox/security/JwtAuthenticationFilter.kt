package com.routebox.routebox.security

import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.security.InvalidTokenException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.security.SecurityConfig.Companion.AUTH_WHITE_LIST
import com.routebox.routebox.security.SecurityConfig.Companion.AUTH_WHITE_PATHS
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtManager: JwtManager,
    private val userService: UserService,
) : OncePerRequestFilter() {

    companion object {
        private const val TOKEN_TYPE_BEARER_PREFIX = "Bearer "
        private val pathMatcher = AntPathMatcher()
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
        if (isRequiredAuth(request.requestURI, request.method)) {
            val accessToken = getAccessTokenFromHeader(request)
            if (accessToken.isNullOrBlank()) {
                throw AuthenticationCredentialsNotFoundException("Access token does not exist.")
            }

            runCatching {
                jwtManager.validate(accessToken)
                val userId = jwtManager.getUserIdFromToken(accessToken)
                val userPrincipal = loadUserPrincipal(userId)
                val authentication = UsernamePasswordAuthenticationToken(userPrincipal, "", userPrincipal.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }.getOrElse { e ->
                if (e is InvalidTokenException) {
                    throw BadCredentialsException("Invalid access token.")
                } else {
                    throw e
                }
            }
        }

        filterChain.doFilter(request, response)
    }

    /**
     * 인증/인가 권한이 필요한 요청인지 확인한다.
     */
    private fun isRequiredAuth(uri: String, method: String): Boolean {
        if (AUTH_WHITE_PATHS.any { authWhitePath -> pathMatcher.match(authWhitePath, uri) }) {
            return false
        }
        if (AUTH_WHITE_LIST.any { (path, httpMethod) -> pathMatcher.match(path, uri) && httpMethod.name() == method }) {
            return false
        }
        return true
    }

    /**
     * Request의 header에서 token을 읽어온다.
     *
     * @param request Request 객체
     * @return Header에서 추출한 token
     */
    private fun getAccessTokenFromHeader(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_TYPE_BEARER_PREFIX)) {
            return null
        }
        return authorizationHeader.substring(TOKEN_TYPE_BEARER_PREFIX.length)
    }

    private fun loadUserPrincipal(userId: Long): UserPrincipal {
        val user = userService.findUserById(userId)
        if (user == null || user.deletedAt != null) {
            throw UserNotFoundException()
        }

        return UserPrincipal(
            userId = user.id,
            socialLoginUid = user.socialLoginUid,
            userRoles = user.roles,
        )
    }
}
