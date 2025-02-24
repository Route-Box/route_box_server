package com.routebox.routebox.domain.user.constant

enum class UserPointTransactionType {
    // 현금 결제를 통해 포인트 충전
    CHARGE_BY_PAYMENT,

    // 루트 구매하여 포인트 사용
    USE_FOR_ROUTE_PURCHASE,

    // 내 루트가 판매되어 포인트 획득
    EARN_FROM_ROUTE_SALE,

    // 포인트를 현금으로 환급함
    WITHDRAW_TO_CASH,
}
