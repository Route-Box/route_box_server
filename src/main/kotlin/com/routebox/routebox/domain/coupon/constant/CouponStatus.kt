package com.routebox.routebox.domain.coupon.constant

enum class CouponStatus {
    READY, // 대기 상태. 아직 쿠폰을 사용할 수 없음
    AVAILABLE, // 쿠폰 사용 가능
    USED, // 사용된 쿠폰
    EXPIRED, // 만료된 쿠폰
}
