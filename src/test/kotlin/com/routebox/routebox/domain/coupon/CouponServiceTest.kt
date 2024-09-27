package com.routebox.routebox.domain.coupon

import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
import com.routebox.routebox.infrastructure.coupon.CouponRepository
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CouponServiceTest {

    @InjectMocks
    private lateinit var sut: CouponService

    @Mock
    private lateinit var couponRepository: CouponRepository

    @Test
    fun `이용 가능한 쿠폰을 조회하면, 상태가 AVAILABLE인 쿠폰 목록이 반환된다`() {
        // given
        val userId = Random.nextLong()
        val expectedResult = listOf(createCoupon(userId = Random.nextLong()))
        given(couponRepository.findByUserIdAndStatus(userId, CouponStatus.AVAILABLE)).willReturn(expectedResult)

        // when
        val actualResult = sut.findAvailableCoupons(userId)

        // then
        then(couponRepository).should().findByUserIdAndStatus(userId, CouponStatus.AVAILABLE)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        Assertions.assertThatIterable(actualResult).isEqualTo(expectedResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(couponRepository).shouldHaveNoMoreInteractions()
    }

    private fun createCoupon(userId: Long): Coupon = Coupon(
        id = Random.nextLong(),
        userId = userId,
        title = RandomStringUtils.random(10),
        type = CouponType.BUY_ROUTE,
        status = CouponStatus.AVAILABLE,
        startedAt = LocalDateTime.now(),
        endedAt = LocalDateTime.now(),
        expiredAt = LocalDateTime.now(),
    )
}
