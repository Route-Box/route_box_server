package com.routebox.routebox.domain.coupon

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "coupon")
@Entity
class Coupon(
    id: Long = 0,
    userId: Long,
    title: String,
    type: CouponType,
    status: CouponStatus,
    startedAt: LocalDateTime,
    endedAt: LocalDateTime?,
    expiredAt: LocalDateTime? = null,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    var id: Long = id
        private set

    var userId: Long = userId
        private set

    var title: String = title
        private set

    @Enumerated(EnumType.STRING)
    var type: CouponType = type
        private set

    @Enumerated(EnumType.STRING)
    var status: CouponStatus = status
        private set

    var startedAt: LocalDateTime = startedAt
        private set

    var endedAt: LocalDateTime? = endedAt
        private set

    var expiredAt: LocalDateTime? = expiredAt
        private set
}
