package com.routebox.routebox.controller.coupon.dto

import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class FindAvailableCouponsResponse(
    val coupons: List<CouponResponse>,
) {
    companion object {
        fun from(coupons: List<Coupon>): FindAvailableCouponsResponse {
            val couponResponses = coupons.map { couponEntity -> CouponResponse.from(couponEntity) }
            return FindAvailableCouponsResponse(couponResponses)
        }
    }

    data class CouponResponse(
        @Schema(description = "Id of coupon", example = "13")
        val id: Long,

        @Schema(description = "쿠폰 제목", example = "회원가입 기념 쿠폰")
        val title: String,

        @Schema(description = "쿠폰 종류")
        val type: CouponType,

        @Schema(description = "쿠폰 상태")
        val status: CouponStatus,

        @Schema(description = "쿠폰 이용이 가능한 시작 시각")
        val startedAt: LocalDateTime,

        @Schema(description = "쿠폰 이용이 가능한 종료 시각")
        val endedAt: LocalDateTime?,

        @Schema(description = "쿠폰이 만료된 시각")
        val expiredAt: LocalDateTime?,
    ) {
        companion object {
            fun from(coupon: Coupon): CouponResponse =
                CouponResponse(
                    id = coupon.id,
                    title = coupon.title,
                    type = coupon.type,
                    status = coupon.status,
                    startedAt = coupon.startedAt,
                    endedAt = coupon.endedAt,
                    expiredAt = coupon.expiredAt,
                )
        }
    }
}
