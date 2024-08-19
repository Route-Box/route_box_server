package com.routebox.routebox.domain.coupon.event

import com.routebox.routebox.domain.coupon.Coupon

data class CouponsIssuedEvent(val coupons: Collection<Coupon>)
