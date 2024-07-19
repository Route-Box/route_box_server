package com.routebox.routebox.infrastructure.config

import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.security.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@EnableJpaAuditing
@Configuration
class JpaConfig {

    @Bean
    fun auditorAware(userService: UserService): AuditorAware<Long> = AuditorAware {
        val principal = Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)

        if (principal.isEmpty || principal.get() == "anonymousUser") {
            return@AuditorAware Optional.empty()
        }

        return@AuditorAware principal
            .map { it as UserPrincipal }
            .map { it.userId }
    }
}
