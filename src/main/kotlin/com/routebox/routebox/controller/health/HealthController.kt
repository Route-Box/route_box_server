package com.routebox.routebox.controller.health

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "서버 상태 확인용 API")
@RequestMapping("/api")
@RestController
class HealthController() {
    @GetMapping("/v1/health")
    fun checkHealth(): String {
        return "Server is running!"
    }
}
