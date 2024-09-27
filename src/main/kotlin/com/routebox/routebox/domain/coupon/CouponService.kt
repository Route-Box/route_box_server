package com.routebox.routebox.domain.coupon

import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.infrastructure.coupon.CouponRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponService(private val couponRepository: CouponRepository) {

    @Transactional(readOnly = true)
    fun findAvailableCoupons(userId: Long): List<Coupon> =
        couponRepository.findByUserIdAndStatus(userId, CouponStatus.AVAILABLE)

    @Transactional
    fun deleteCoupon(coupon: Coupon) {
        couponRepository.delete(coupon)
    }
}
