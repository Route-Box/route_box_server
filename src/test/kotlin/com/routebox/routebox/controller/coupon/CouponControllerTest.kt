package com.routebox.routebox.controller.coupon

import com.routebox.routebox.application.coupon.FindAvailableCouponsUseCase
import com.routebox.routebox.config.ControllerTestConfig
import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
import com.routebox.routebox.domain.user.constant.UserRoleType
import com.routebox.routebox.security.UserPrincipal
import org.apache.commons.lang3.RandomStringUtils
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@Import(ControllerTestConfig::class)
@WebMvcTest(controllers = [CouponController::class])
class CouponControllerTest @Autowired constructor(private val mvc: MockMvc) {

    @MockBean
    lateinit var findAvailableCouponsUseCase: FindAvailableCouponsUseCase

    @Test
    fun `이용 가능한 쿠폰을 조회하면, 상태가 AVAILABLE인 쿠폰 목록이 반환된다`() {
        // given
        val userId = Random.nextLong()
        val expectedResult = listOf(createCoupon(userId))
        given(findAvailableCouponsUseCase.invoke(userId)).willReturn(expectedResult)

        // when & then
        mvc.perform(
            get("/api/v1/coupons/available")
                .with(user(createUserPrincipal(userId))),
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.coupons.size()").value(expectedResult.size))
        then(findAvailableCouponsUseCase).should().invoke(userId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(findAvailableCouponsUseCase).shouldHaveNoMoreInteractions()
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

    private fun createUserPrincipal(userId: Long) = UserPrincipal(
        userId = userId,
        socialLoginUid = userId.toString(),
        userRoles = setOf(UserRoleType.USER),
    )
}
