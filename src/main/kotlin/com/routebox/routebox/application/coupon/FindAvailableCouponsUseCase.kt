package com.routebox.routebox.application.coupon

import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.CouponService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FindAvailableCouponsUseCase(private val couponService: CouponService) {
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): List<Coupon> =
        couponService.findAvailableCoupons(userId)
}
