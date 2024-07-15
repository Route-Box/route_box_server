package com.routebox.routebox.domain.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity protected constructor(
    createdAt: LocalDateTime = LocalDateTime.MIN,
    updatedAt: LocalDateTime = LocalDateTime.MIN,
    createdBy: Long = 0,
    updatedBy: Long = 0,
) : TimeTrackedBaseEntity(createdAt, updatedAt) {

    @Column(nullable = false, updatable = false)
    @CreatedBy
    var createdBy = createdBy
        protected set

    @Column(nullable = false)
    @LastModifiedBy
    var updatedBy = updatedBy
        protected set
}
