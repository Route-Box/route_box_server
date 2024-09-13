package com.routebox.routebox.domain.auth

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "withdrawal_histories")
@Entity
class WithdrawalHistory(
    id: Long = 0,
    userId: Long,
    reasonType: WithdrawalReasonType?,
    reasonDetail: String?,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_history_id")
    val id: Long = id

    var userId: Long = userId

    @Enumerated(EnumType.STRING)
    var reasonType: WithdrawalReasonType? = reasonType
        private set

    var reasonDetail: String? = reasonDetail
        private set
}
