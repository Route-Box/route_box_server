package com.routebox.routebox.security

import com.routebox.routebox.domain.user.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class CustomUserDetailsService {

    @Bean
    fun userDetailsService(userService: UserService): UserDetailsService =
        UserDetailsService { username ->
            val user = userService.getUserById(username.toLong())
            UserPrincipal(
                userId = user.id,
                socialLoginUid = user.socialLoginUid,
                userRoles = user.roles,
            )
        }
}
