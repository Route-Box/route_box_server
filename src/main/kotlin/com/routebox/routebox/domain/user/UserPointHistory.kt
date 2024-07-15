package com.routebox.routebox.domain.user

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.constant.UserPointTransactionType
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
    transactionType: UserPointTransactionType,
    amount: Int,
    id: Long = 0,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_history_id")
    val id: Long = id

    val userId: Long = userId

    @Enumerated(EnumType.STRING)
    val transactionType: UserPointTransactionType = transactionType

    val amount: Int = amount
}
