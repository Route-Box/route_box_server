package com.routebox.routebox.controller.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Value("\${routebox.app-version}")
    lateinit var appVersion: String

    @Value("\${swagger.server-url}")
    lateinit var serverUrl: String

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
            .components(
                Components().addSecuritySchemes(
                    "access-token",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"),
                ),
            )
            .servers(listOf(Server().url(serverUrl)))
}
