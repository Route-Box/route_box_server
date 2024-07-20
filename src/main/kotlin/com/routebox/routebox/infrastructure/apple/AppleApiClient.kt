package com.routebox.routebox.infrastructure.apple

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "AppleApiClient",
    url = "https://appleid.apple.com",
)
interface AppleApiClient {
    @GetMapping("/auth/keys")
    fun getAuthKeys(): AppleAuthKeys
}
