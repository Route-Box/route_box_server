package com.routebox.routebox.domain.notification

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Notification(
    userId: Long,
    content: String,
    id: Long = 0,
    isRead: Boolean = false,
) : TimeTrackedBaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    val id: Long = id

    val userId: Long = userId

    var isRead: Boolean = isRead
        private set

    val content: String = content
}
