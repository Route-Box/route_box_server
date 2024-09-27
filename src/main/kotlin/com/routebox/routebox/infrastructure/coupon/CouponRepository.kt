package com.routebox.routebox.infrastructure.coupon

import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
    fun findByUserIdAndStatus(userId: Long, status: CouponStatus): List<Coupon>
}
