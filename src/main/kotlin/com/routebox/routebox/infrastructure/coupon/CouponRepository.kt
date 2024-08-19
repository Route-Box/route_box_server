package com.routebox.routebox.infrastructure.coupon

import com.routebox.routebox.domain.coupon.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long>
