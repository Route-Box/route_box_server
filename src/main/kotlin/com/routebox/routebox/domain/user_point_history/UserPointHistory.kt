package com.routebox.routebox.domain.user_point_history

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.user.constant.UserPointTransactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserPointHistory(
    userId: Long,
    routeId: Long?,
    transactionType: UserPointTransactionType,
    amount: Int,
    id: Long = 0,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = id

    @Column(name = "user_id", nullable = false)
    val userId: Long = userId

    @Column(name = "route_id", nullable = true)
    val routeId: Long? = routeId

    @Enumerated(EnumType.STRING)
    val transactionType: UserPointTransactionType = transactionType

    val amount: Int = amount
}
