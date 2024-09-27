package com.routebox.routebox.application.coupon

import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.CouponService
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
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
class FindAvailableCouponsUseCaseTest {

    @InjectMocks
    private lateinit var sut: FindAvailableCouponsUseCase

    @Mock
    private lateinit var couponService: CouponService

    @Test
    fun `이용 가능한 쿠폰을 조회하면, 상태가 AVAILABLE인 쿠폰 목록이 반환된다`() {
        // given
        val userId = Random.nextLong()
        val expectedResult = listOf(createCoupon(userId = Random.nextLong()))
        given(couponService.findAvailableCoupons(userId)).willReturn(expectedResult)

        // when
        val actualResult = sut.invoke(userId)

        // then
        then(couponService).should().findAvailableCoupons(userId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        Assertions.assertThatIterable(actualResult).isEqualTo(expectedResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(couponService).shouldHaveNoMoreInteractions()
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
