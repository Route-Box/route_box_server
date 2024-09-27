package com.routebox.routebox.domain.coupon.event

import com.routebox.routebox.infrastructure.coupon.CouponRepository
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CouponIssuedEventListener(private val couponRepository: CouponRepository) {

    /*
    TODO: 쿠폰 발행 기능은 이벤트를 발행한 기능과 별도의 transaction에서 동작하므로 쿠폰 발행에 실패했을 때에 대한 복구/대응 방법을 고려해야 한다.
    쿠폰 발행이 실패했을 때 조치 방법 후보
    - Slack 연동을 통해 관리자에게 알림 보내기
    - Scheduler를 통해 회원가입을 진행한 유저에게 쿠폰이 정상적으로 발행되었는지 확인
        이를 위해 outbox pattern 등 부가적인 기법을 사용하는 것 까지는 적절하지 않아보임.
        `handle()`의 기능 자체가 단순하고, DB가 정상 동작하지 않는 경우를 제외하면 쿠폰 발행에 실패하는 경우가 거의 없을 것으로 보이기 때문.
     */

    @Retryable(
        value = [Exception::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1500),
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: CouponsIssuedEvent) {
        couponRepository.saveAll(event.coupons)
    }
}
