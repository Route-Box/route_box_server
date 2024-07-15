package com.routebox.routebox.domain.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 생성 시각, 정보 수정 시각을 추적하는 base entity
 */
@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class TimeTrackedBaseEntity protected constructor(
    createdAt: LocalDateTime = LocalDateTime.MIN,
    updatedAt: LocalDateTime = LocalDateTime.MIN,
) {
    @Column(nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = createdAt
        protected set

    @Column(nullable = false)
    @LastModifiedDate
    var updatedAt: LocalDateTime = updatedAt
        protected set
}
