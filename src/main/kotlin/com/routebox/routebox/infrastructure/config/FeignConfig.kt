package com.routebox.routebox.infrastructure.config

import feign.Logger
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["com.routebox.routebox"])
@Configuration
class FeignConfig {
    // Local 환경에서 테스트, 디버깅 용으로 사용할 경우에만 주석 해제
    // @Bean
    // fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL
}
