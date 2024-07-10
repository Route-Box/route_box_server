package com.routebox.routebox.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@EnableJpaAuditing
@Configuration
class JpaConfig {

    // TODO: 유저 entity 및 기능 구현 후 수정 필요
    @Bean
    fun auditorAware(): AuditorAware<Long> = AuditorAware { Optional.of(1L) }
}
