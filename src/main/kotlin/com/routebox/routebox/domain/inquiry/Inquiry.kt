package com.routebox.routebox.domain.inquiry

import InquiryType
import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "inquiry")
data class Inquiry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    val inquiryId: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: InquiryType,

    @Column(name = "content", nullable = false, length = 400)
    val content: String,

    @Column(name = "reply", length = 400)
    val reply: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: InquiryStatus,
) : TimeTrackedBaseEntity()
