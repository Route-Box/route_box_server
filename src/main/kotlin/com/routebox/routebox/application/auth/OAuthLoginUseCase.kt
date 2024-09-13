package com.routebox.routebox.application.auth

import com.routebox.routebox.application.auth.dto.LoginResult
import com.routebox.routebox.application.auth.dto.OAuthLoginCommand
import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.coupon.Coupon
import com.routebox.routebox.domain.coupon.constant.CouponStatus
import com.routebox.routebox.domain.coupon.constant.CouponType
import com.routebox.routebox.domain.coupon.event.CouponsIssuedEvent
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.user.UserWithdrawnException
import jakarta.validation.Valid
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class OAuthLoginUseCase(
    private val userService: UserService,
    private val authService: AuthService,
    private val eventPublisher: ApplicationEventPublisher,
) {
    /**
     * OAuth(Kakao, Apple) 로그인.
     *
     * Social login uid를 조회한 후, 다음 로직을 수행한다.
     * - 신규 유저라면: 유저 데이터 생성 및 저장
     * - 기존 유저라면: 유저 데이터 조회
     *
     * 탈퇴한 유저라면 탈퇴한 유저 예외를 발생시킨다.
     * 만약 유저 데이터를 생성했을 경우, 회원가입 기념 쿠폰을 세 장 지급할 수 있도록 이벤트를 발행한다.
     *
     * 이후 생성 또는 조회한 유저 정보로 access token과 refresh token을 생성하여 반환한다.
     *
     * @param command
     * @return 로그인 결과로 신규 유저인지에 대한 정보, access token 정보, refresh token 정보를 응답한다.
     */
    @Transactional
    operator fun invoke(@Valid command: OAuthLoginCommand): LoginResult {
        val oAuthUserInfo = authService.getUserInfo(command.loginType, command.token)

        var isSignUpProceeded = false
        val user = userService.findUserBySocialLoginUid(oAuthUserInfo.uid)
            ?: userService.createNewUser(command.loginType, oAuthUserInfo.uid)
                .also { isSignUpProceeded = true }

        if (user.deletedAt != null) {
            throw UserWithdrawnException()
        }

        val result = LoginResult(
            isNew = user.isOnboardingComplete(),
            loginType = command.loginType,
            accessToken = authService.issueAccessToken(user),
            refreshToken = authService.issueRefreshToken(user),
        )

        if (isSignUpProceeded) {
            issueSingUpCoupons(userId = user.id)
        }

        return result
    }

    /**
     * 회원 가입 시 3개의 쿠폰 발행
     *
     * @param userId 쿠폰을 발행할 대상(사용자)의 id
     */
    private fun issueSingUpCoupons(userId: Long) {
        val coupons = List(3) {
            Coupon(
                userId = userId,
                title = "회원가입 감사 쿠폰",
                type = CouponType.BUY_ROUTE,
                status = CouponStatus.AVAILABLE,
                startedAt = LocalDateTime.now(),
                endedAt = null,
            )
        }
        eventPublisher.publishEvent(CouponsIssuedEvent(coupons))
    }
}
