package com.routebox.routebox.controller.coupon

import com.routebox.routebox.application.coupon.FindAvailableCouponsUseCase
import com.routebox.routebox.controller.coupon.dto.FindAvailableCouponsResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "쿠폰 관련 API")
@RequestMapping("/api")
@RestController
class CouponController(
    private val findAvailableCouponsUseCase: FindAvailableCouponsUseCase,
) {
    @Operation(
        summary = "이용 가능한 쿠폰 목록 조회",
        description = "현재 이용 가능한 쿠폰 목록을 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/coupons/available")
    fun findAvailableCoupons(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): FindAvailableCouponsResponse {
        val availableCoupons = findAvailableCouponsUseCase(userId = userPrincipal.userId)
        return FindAvailableCouponsResponse.from(availableCoupons)
    }
}
