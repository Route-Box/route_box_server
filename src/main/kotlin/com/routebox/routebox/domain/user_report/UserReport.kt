package com.routebox.routebox.domain.user_report

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "user_report")
@Entity
class UserReport(
    id: Long = 0,
    reporterId: Long,
    reportedUserId: Long,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_report_id")
    val id: Long = id

    val reporterId = reporterId

    val reportedUserId = reportedUserId
}
