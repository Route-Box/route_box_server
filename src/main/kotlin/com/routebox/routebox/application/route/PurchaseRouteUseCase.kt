package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.PurchaseRouteCommand
import com.routebox.routebox.domain.coupon.CouponService
import com.routebox.routebox.domain.purchased_route.PurchasedRoute
import com.routebox.routebox.domain.purchased_route.PurchasedRouteService
import com.routebox.routebox.domain.purchased_route.constant.RoutePaymentMethod.COUPON
import com.routebox.routebox.domain.purchased_route.constant.RoutePaymentMethod.POINT
import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.coupon.NoAvailableCouponException
import com.routebox.routebox.exception.route.RouteNotFoundException
import com.routebox.routebox.exception.user.UserNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class PurchaseRouteUseCase(
    private val userService: UserService,
    private val couponService: CouponService,
    private val routeService: RouteService,
    private val purchasedRouteService: PurchasedRouteService,
) {
    /**
     * 루트를 구매합니다. 루트는 쿠폰과 포인트를 사용하여 구매할 수 있다.
     * 쿠폰을 사용하여 루트를 구매할 경우, 마감일이 얼마 남지 않은 쿠폰이 자동으로 사용된다.
     *
     * @throws NoAvailableCouponException (쿠폰으로 구매 시) 이용 가능한 쿠폰이 없는 경우
     * @throws UserNotFoundException `buyerId`에 해당하는 유저가 없는 경우
     * @throws RouteNotFoundException `routeId`에 해당하는, 구매할 루트 정보가 없는 경우
     */
    @Transactional
    operator fun invoke(command: PurchaseRouteCommand) {
        when (command.paymentMethod) {
            POINT -> {
                // TODO: 포인트 결제 기능 추가
                throw IllegalArgumentException("아직 사용할 수 없는 기능입니다.")
            }

            COUPON -> {
                // TODO: 쿠폰 사용에 대한 log(history) 저장에 대한 고민 필요
                // 이용 가능한 쿠폰 중, 종료일이 가장 빠른(현재에 가까운) 쿠폰 단건 조회
                val coupon = couponService.findAvailableCoupons(command.buyerId)
                    // 종료일이 null인 경우, 즉 종료 기한 없이 무제한 사용이 가능한 쿠폰은 가장 마지막에 위치
                    .sortedBy { coupon -> coupon.endedAt ?: LocalDateTime.MAX }
                    .firstOrNull()
                if (coupon == null) {
                    throw NoAvailableCouponException()
                }
                couponService.deleteCoupon(coupon)
            }
        }

        val buyer = userService.getUserById(command.buyerId)
        val route = routeService.getRouteById(command.routeId)
        purchasedRouteService.createPurchasedRoute(PurchasedRoute.from(route, buyer))
    }
}
