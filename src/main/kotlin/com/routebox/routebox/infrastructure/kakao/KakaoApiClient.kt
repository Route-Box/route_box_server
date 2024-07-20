package com.routebox.routebox.infrastructure.kakao

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "KakaoApiClient",
    url = "https://kapi.kakao.com",
)
interface KakaoApiClient {
    @GetMapping(
        value = ["/v2/user/me"],
        headers = ["Content-type=application/x-www-form-urlencoded;charset=utf-8"],
    )
    fun getUserInfo(@RequestHeader("Authorization") authorizationHeader: String): OAuthUserInfoResponse
}
