package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.PurchaseRouteCommand
import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.CouponService
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
import com.routebox.routebox.domain.purchased_route.PurchasedRouteService
import com.routebox.routebox.domain.purchased_route.constant.RoutePaymentMethod
import com.routebox.routebox.domain.route.Route
import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.willDoNothing
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class PurchaseRouteUseCaseTest {

    @InjectMocks
    lateinit var sut: PurchaseRouteUseCase

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var couponService: CouponService

    @Mock
    lateinit var routeService: RouteService

    @Mock
    lateinit var purchasedRouteService: PurchasedRouteService

    @Test
    fun `쿠폰으로 루트를 구매하면, 쿠폰이 하나 차감되고 구매한 루트의 복사본이 생성된다`() {
        // given
        val command = PurchaseRouteCommand(
            buyerId = Random.nextLong(),
            routeId = Random.nextLong(),
            paymentMethod = RoutePaymentMethod.COUPON,
        )
        val availableCoupons = listOf(
            createCoupon(command.buyerId, LocalDateTime.of(2024, 12, 31, 0, 0)),
            createCoupon(command.buyerId, null),
            createCoupon(command.buyerId, LocalDateTime.of(2024, 1, 1, 0, 0)),
        )
        val deletedCoupon = argumentCaptor<Coupon>()
        given(couponService.findAvailableCoupons(command.buyerId))
            .willReturn(availableCoupons)
        willDoNothing()
            .given(couponService).deleteCoupon(any())
        given(userService.getUserById(command.buyerId))
            .willReturn(createUser(command.buyerId))
        given(routeService.getRouteById(command.routeId))
            .willReturn(createRoute(command.routeId, createUser(Random.nextLong())))
        willDoNothing()
            .given(purchasedRouteService).createPurchasedRoute(any())

        // when
        sut.invoke(command)

        // then
        then(couponService).should().findAvailableCoupons(command.buyerId)
        then(couponService).should().deleteCoupon(deletedCoupon.capture())
        then(userService).should().getUserById(command.buyerId)
        then(routeService).should().getRouteById(command.routeId)
        then(purchasedRouteService).should().createPurchasedRoute(any())
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(deletedCoupon.allValues.size).isEqualTo(1)
        assertThat(deletedCoupon.firstValue).isEqualTo(availableCoupons[2]) // 종료일이 24.01.01인 쿠폰이 사용되어야 함
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(userService).shouldHaveNoMoreInteractions()
        then(couponService).shouldHaveNoMoreInteractions()
        then(routeService).shouldHaveNoMoreInteractions()
        then(purchasedRouteService).shouldHaveNoMoreInteractions()
    }

    private fun createUser(userId: Long) = User(
        id = userId,
        loginType = LoginType.KAKAO,
        socialLoginUid = RandomStringUtils.random(10),
        nickname = RandomStringUtils.random(10),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )

    private fun createRoute(routeId: Long, user: User) = Route(
        id = routeId,
        user = user,
        name = RandomStringUtils.random(8, true, true),
        description = RandomStringUtils.random(8, true, true),
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        whoWith = "친구",
        numberOfPeople = 2,
        numberOfDays = "2박3일",
        style = arrayOf("힐링"),
        transportations = "뚜벅뚜벅",
    )

    private fun createCoupon(userId: Long, endedAt: LocalDateTime?): Coupon = Coupon(
        id = Random.nextLong(),
        userId = userId,
        title = RandomStringUtils.random(10),
        type = CouponType.BUY_ROUTE,
        status = CouponStatus.AVAILABLE,
        startedAt = LocalDateTime.now(),
        endedAt = endedAt,
        expiredAt = LocalDateTime.now(),
    )
}
