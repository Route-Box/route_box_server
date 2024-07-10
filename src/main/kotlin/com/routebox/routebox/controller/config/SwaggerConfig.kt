package com.routebox.routebox.controller.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Value("\${routebox.app-version}")
    lateinit var appVersion: String

    // TODO: Spring Security 도입 후, 인증/인가 설정 추가 필요
    @Bean
    fun openApi(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Route Box API Server")
                    .version(appVersion),
            ).externalDocs(
                ExternalDocumentation()
                    .url("https://github.com/Route-Box")
                    .description("GitHub Organization for team Route Box"),
            )
}
